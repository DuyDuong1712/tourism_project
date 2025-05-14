package com.travel.travel_booking_service.service.impl;

import com.travel.travel_booking_service.dto.BookingDTO;
import com.travel.travel_booking_service.dto.BookingRequestDTO;
import com.travel.travel_booking_service.entity.Booking;
import com.travel.travel_booking_service.entity.Tour;
import com.travel.travel_booking_service.entity.User;
import com.travel.travel_booking_service.enums.ErrorCode;
import com.travel.travel_booking_service.exception.BusinessException;
import com.travel.travel_booking_service.exception.ResourceNotFoundException;
import com.travel.travel_booking_service.mapper.BookingMapper;
import com.travel.travel_booking_service.repository.BookingRepository;
import com.travel.travel_booking_service.repository.TourRepository;
import com.travel.travel_booking_service.repository.UserRepository;
import com.travel.travel_booking_service.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final TourRepository tourRepository;
    private final UserRepository userRepository;
    private final BookingMapper bookingMapper;

    @Override
    public BookingDTO createBooking(BookingRequestDTO bookingRequest) {
        validateBooking(bookingRequest);

        Tour tour = tourRepository.findById(bookingRequest.getTourId())
                .orElseThrow(() -> new ResourceNotFoundException("Tour", "id", bookingRequest.getTourId()));

        User user = userRepository.findById(bookingRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", bookingRequest.getUserId()));

        if (!isTourAvailable(tour, bookingRequest.getNumberOfPeople())) {
            throw new BusinessException(ErrorCode.TOUR_NOT_AVAILABLE);
        }

        Booking booking = bookingMapper.toEntity(bookingRequest);
        booking.setTour(tour);
        booking.setUser(user);
        booking.setStatus("PENDING");
        booking.setCreatedAt(LocalDateTime.now());
        booking.setTotalAmount(calculateTotalAmount(tour, bookingRequest.getNumberOfPeople()));

        Booking savedBooking = bookingRepository.save(booking);
        updateTourAvailability(tour, bookingRequest.getNumberOfPeople());
        return bookingMapper.toDTO(savedBooking);
    }

    @Override
    public BookingDTO updateBooking(Long id, BookingRequestDTO bookingRequest) {
        validateBooking(bookingRequest);

        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", id));

        if (!"PENDING".equals(existingBooking.getStatus())) {
            throw new BusinessException(ErrorCode.BOOKING_CANNOT_BE_UPDATED);
        }

        Tour tour = tourRepository.findById(bookingRequest.getTourId())
                .orElseThrow(() -> new ResourceNotFoundException("Tour", "id", bookingRequest.getTourId()));

        User user = userRepository.findById(bookingRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", bookingRequest.getUserId()));

        int peopleDifference = bookingRequest.getNumberOfPeople() - existingBooking.getNumberOfPeople();
        if (peopleDifference > 0 && !isTourAvailable(tour, peopleDifference)) {
            throw new BusinessException(ErrorCode.TOUR_NOT_AVAILABLE);
        }

        bookingMapper.updateEntityFromDTO(bookingRequest, existingBooking);
        existingBooking.setTour(tour);
        existingBooking.setUser(user);
        existingBooking.setUpdatedAt(LocalDateTime.now());
        existingBooking.setTotalAmount(calculateTotalAmount(tour, bookingRequest.getNumberOfPeople()));

        Booking updatedBooking = bookingRepository.save(existingBooking);
        updateTourAvailability(tour, peopleDifference);
        return bookingMapper.toDTO(updatedBooking);
    }

    @Override
    public void cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", id));

        if (!"PENDING".equals(booking.getStatus()) && !"CONFIRMED".equals(booking.getStatus())) {
            throw new BusinessException(ErrorCode.BOOKING_CANNOT_BE_CANCELLED);
        }

        booking.setStatus("CANCELLED");
        booking.setUpdatedAt(LocalDateTime.now());
        bookingRepository.save(booking);

        // Restore tour availability
        Tour tour = booking.getTour();
        tour.setAvailableSlots(tour.getAvailableSlots() + booking.getNumberOfPeople());
        tourRepository.save(tour);
    }

    @Override
    @Transactional(readOnly = true)
    public BookingDTO getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", id));
        return bookingMapper.toDTO(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookingDTO> getAllBookings(Pageable pageable) {
        return bookingRepository.findAll(pageable)
                .map(bookingMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookingDTO> getBookingsByUserId(Long userId, Pageable pageable) {
        return bookingRepository.findByUserId(userId, pageable)
                .map(bookingMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookingDTO> getBookingsByTourId(Long tourId, Pageable pageable) {
        return bookingRepository.findByTourId(tourId, pageable)
                .map(bookingMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingDTO> getUpcomingBookings(Long userId) {
        return bookingRepository.findUpcomingBookingsByUserId(userId).stream()
                .map(bookingMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void updateBookingStatus(Long id, String status) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", id));
        booking.setStatus(status);
        booking.setUpdatedAt(LocalDateTime.now());
        bookingRepository.save(booking);
    }

    @Override
    public void validateBooking(BookingRequestDTO bookingRequest) {
        if (bookingRequest.getNumberOfPeople() <= 0) {
            throw new BusinessException(ErrorCode.VALIDATION_ERROR, "Number of people must be greater than zero");
        }

        if (bookingRequest.getTourId() == null) {
            throw new BusinessException(ErrorCode.VALIDATION_ERROR, "Tour ID is required");
        }

        if (bookingRequest.getUserId() == null) {
            throw new BusinessException(ErrorCode.VALIDATION_ERROR, "User ID is required");
        }
    }

    private boolean isTourAvailable(Tour tour, int numberOfPeople) {
        return tour.getAvailableSlots() >= numberOfPeople && "ACTIVE".equals(tour.getStatus());
    }

    private void updateTourAvailability(Tour tour, int numberOfPeople) {
        tour.setAvailableSlots(tour.getAvailableSlots() - numberOfPeople);
        tourRepository.save(tour);
    }

    private double calculateTotalAmount(Tour tour, int numberOfPeople) {
        return tour.getPrice() * numberOfPeople;
    }
} 