package com.travel.travel_booking_service.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.travel.travel_booking_service.entity.Booking;
import com.travel.travel_booking_service.entity.BookingPassenger;
import com.travel.travel_booking_service.entity.TourDetail;
import com.travel.travel_booking_service.enums.ErrorCode;
import com.travel.travel_booking_service.enums.PassengerType;
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
                .paymentMethod(bookingRequest.getPaymentMethod())
                .paymentStatus("PENDING")
                .build();

        bookingRepository.save(booking);

        // Lưu passengers
        List<BookingPassenger> passengers = bookingRequest.getPassengers().stream()
                .map(dto -> BookingPassenger.builder()
                        .booking(booking)
                        .fullName(dto.getFullname())
                        .dateOfBirth(LocalDate.parse(dto.getBirthDate()))
                        .gender(dto.getGender())
                        .passengerType(calculatePassengerType(LocalDate.parse(dto.getBirthDate())))
                        .build())
                .collect(Collectors.toList());

        bookingPassengerRepository.saveAll(passengers);

        return booking.getId().toString();
    }

    @Override
    public void updateBookingStatus(Long id, String status) {

    }


    private String calculatePassengerType(LocalDate date) {
        LocalDate today = LocalDate.now();
        int age = today.getYear() - date.getYear();

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
