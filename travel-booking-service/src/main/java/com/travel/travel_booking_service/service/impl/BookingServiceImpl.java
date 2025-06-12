package com.travel.travel_booking_service.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.travel.travel_booking_service.entity.Booking;
import com.travel.travel_booking_service.entity.BookingPassenger;
import com.travel.travel_booking_service.entity.TourDetail;
import com.travel.travel_booking_service.entity.User;
import com.travel.travel_booking_service.enums.BookingStatus;
import com.travel.travel_booking_service.enums.ErrorCode;
import com.travel.travel_booking_service.enums.PassengerType;
import com.travel.travel_booking_service.enums.PaymentStatus;
import com.travel.travel_booking_service.exception.AppException;
import com.travel.travel_booking_service.repository.BookingPassengerRepository;
import com.travel.travel_booking_service.repository.BookingRepository;
import com.travel.travel_booking_service.repository.TourDetailRepository;
import com.travel.travel_booking_service.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.travel_booking_service.dto.request.BookingRequest;
import com.travel.travel_booking_service.dto.response.BookingResponse;
import com.travel.travel_booking_service.service.BookingService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingServiceImpl implements BookingService {

    TourDetailRepository tourDetailRepository;
    UserRepository userRepository;
    BookingRepository bookingRepository;
    BookingPassengerRepository bookingPassengerRepository;



    @Override
    public String createPendingBooking(BookingRequest bookingRequest) {

        // Validate tour detail exists
        TourDetail tourDetail = tourDetailRepository.findById(bookingRequest.getTourDetailId())
                .orElseThrow(() -> new AppException(ErrorCode.TOUR_NOT_FOUND));

        // Validate user exists
        User user = userRepository.findById(bookingRequest.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Tạo booking với trạng thái PENDING
        Booking booking = Booking.builder()
                .tourDetail(tourDetailRepository.findById(bookingRequest.getTourDetailId()).orElseThrow(() -> new AppException(ErrorCode.TOUR_NOT_FOUND)))
                .customer(userRepository.findById(bookingRequest.getUserId()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)))
                .fullName(bookingRequest.getFullname())
                .email(bookingRequest.getEmail())
                .phoneNumber(bookingRequest.getPhone())
                .address(bookingRequest.getAddress())
                .adultCount(bookingRequest.getAdultQuantity())
                .childrenCount(bookingRequest.getChildrenQuantity())
                .childCount(bookingRequest.getChildQuantity())
                .babyCount(bookingRequest.getBabyQuantity())
                .singleRoomCount(bookingRequest.getSingleRoomSupplementQuantity())
                .subtotal(bookingRequest.getOriginalPrice())
                .discountAmount(bookingRequest.getDiscountAmount())
                .totalAmount(bookingRequest.getFinalPrice())
                .note(bookingRequest.getNote())
                .bookingStatus(BookingStatus.PENDING.name())
                .paymentStatus(PaymentStatus.PENDING.name())
                .build();

        Booking savedBooking = bookingRepository.save(booking);

        // Lưu passengers với proper validation
        if (bookingRequest.getPassengers() != null && !bookingRequest.getPassengers().isEmpty()) {
            List<BookingPassenger> passengers = bookingRequest.getPassengers().stream()
                    .filter(dto -> dto.getFullname() != null && !dto.getFullname().trim().isEmpty())
                    .map(dto -> {
                        LocalDate birthDate = null;
                        if (dto.getBirthDate() != null && !dto.getBirthDate().trim().isEmpty()) {
                            try {
                                birthDate = LocalDate.parse(dto.getBirthDate());
                            } catch (Exception e) {
                                // Log error but continue - birth date might be optional for some passenger types
                                System.err.println("Error parsing birth date: " + dto.getBirthDate());
                            }
                        }

                        return BookingPassenger.builder()
                                .booking(savedBooking)
                                .fullName(dto.getFullname())
                                .dateOfBirth(birthDate)
                                .gender(dto.getGender())
                                .passengerType(birthDate != null ? calculatePassengerType(birthDate) : "ADULT")
                                .build();
                    })
                    .collect(Collectors.toList());

            bookingPassengerRepository.saveAll(passengers);
        }

        return savedBooking.getId().toString();
    }

    @Override
    public void updateBookingStatus(Long id, String status) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));

        booking.setPaymentStatus(status);

        if ("SUCCESS".equals(status)) {
            booking.setBookingStatus(BookingStatus.CONFIRMED.name());
            booking.setConfirmedAt(LocalDateTime.now());
            booking.setPaymentDate(LocalDateTime.now());
        } else if ("FAILED".equals(status)) {
            booking.setBookingStatus(BookingStatus.CANCELLED.name());
            booking.setCancelledAt(LocalDateTime.now());
            booking.setCancellationReason("Payment failed");
        }

        bookingRepository.save(booking);
    }


    private String calculatePassengerType(LocalDate birthDate) {
        if (birthDate == null) {
            return PassengerType.ADULT.getDescription();
        }

        LocalDate today = LocalDate.now();
        int age = today.getYear() - birthDate.getYear();

        // Điều chỉnh tuổi nếu chưa đến sinh nhật
        if (birthDate.getDayOfYear() > today.getDayOfYear()) {
            age--;
        }

        if (age >= 12) {
            return PassengerType.ADULT.getDescription();
        } else if (age >= 5) {
            return PassengerType.CHILDREN.getDescription();
        } else if (age >= 2) {
            return PassengerType.CHILD.getDescription();
        } else {
            return PassengerType.BABY.getDescription();
        }
    }
}
