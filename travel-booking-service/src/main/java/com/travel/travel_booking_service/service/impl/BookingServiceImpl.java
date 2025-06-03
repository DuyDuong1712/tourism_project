package com.travel.travel_booking_service.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.travel.travel_booking_service.dto.request.BookingRequest;
import com.travel.travel_booking_service.dto.response.BookingResponse;
import com.travel.travel_booking_service.service.BookingService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingServiceImpl implements BookingService {
    @Override
    public BookingResponse createBooking(BookingRequest request) {
        return null;
    }

    @Override
    public List<BookingResponse> getAllBookings() {
        return List.of();
    }

    @Override
    public BookingResponse getBookingById(Long id) {
        return null;
    }

    @Override
    public BookingResponse updateBooking(Long id, BookingRequest request) {
        return null;
    }

    @Override
    public void deleteBooking(Long id) {}

    @Override
    public List<BookingResponse> searchBookings(String keyword) {
        return List.of();
    }

    @Override
    public List<BookingResponse> getBookingsByUserId(Long userId) {
        return List.of();
    }

    @Override
    public List<BookingResponse> getBookingsByTourId(Long tourId) {
        return List.of();
    }
    //    BookingRepository bookingRepository;
    //
    //    @Override
    //    @Transactional
    //    public BookingResponse createBooking(BookingRequest request) {
    //        Booking booking = Booking.builder()
    //                .bookingDate(request.getBookingDate())
    //                .numberOfPeople(request.getNumberOfPeople())
    //                .totalPrice(request.getTotalPrice())
    //                .status(request.getStatus())
    //                .build();
    //
    //        return convertToResponse(bookingRepository.save(booking));
    //    }
    //
    //    @Override
    //    public List<BookingResponse> getAllBookings() {
    //        return bookingRepository.findAll().stream()
    //                .map(this::convertToResponse)
    //                .collect(Collectors.toList());
    //    }
    //
    //    @Override
    //    public BookingResponse getBookingById(Long id) {
    //        return convertToResponse(bookingRepository.findById(id)
    //                .orElseThrow(() -> new RuntimeException("Booking not found")));
    //    }
    //
    //    @Override
    //    @Transactional
    //    public BookingResponse updateBooking(Long id, BookingRequest request) {
    //        Booking booking = bookingRepository.findById(id)
    //                .orElseThrow(() -> new RuntimeException("Booking not found"));
    //
    //        booking.setBookingDate(request.getBookingDate());
    //        booking.setNumberOfPeople(request.getNumberOfPeople());
    //        booking.setTotalPrice(request.getTotalPrice());
    //        booking.setStatus(request.getStatus());
    //
    //        return convertToResponse(bookingRepository.save(booking));
    //    }
    //
    //    @Override
    //    @Transactional
    //    public void deleteBooking(Long id) {
    //        bookingRepository.deleteById(id);
    //    }
    //
    //    @Override
    //    public List<BookingResponse> searchBookings(String keyword) {
    //        return bookingRepository.searchBookings(keyword).stream()
    //                .map(this::convertToResponse)
    //                .collect(Collectors.toList());
    //    }
    //
    //    @Override
    //    public List<BookingResponse> getBookingsByUserId(Long userId) {
    //        return bookingRepository.findByUserId(userId).stream()
    //                .map(this::convertToResponse)
    //                .collect(Collectors.toList());
    //    }
    //
    //    @Override
    //    public List<BookingResponse> getBookingsByTourId(Long tourId) {
    //        return bookingRepository.findByTourId(tourId).stream()
    //                .map(this::convertToResponse)
    //                .collect(Collectors.toList());
    //    }
    //
    //    private BookingResponse convertToResponse(Booking booking) {
    //        return BookingResponse.builder()
    //                .id(booking.getId())
    //                .bookingDate(booking.getBookingDate())
    //                .numberOfPeople(booking.getNumberOfPeople())
    //                .totalPrice(booking.getTotalPrice())
    //                .status(booking.getStatus())
    //                .tourId(booking.getTour().getId())
    //                .userId(booking.getUser().getId())
    //                .build();
    //    }
}
