package com.travel.travel_booking_service.service;

import java.util.List;

import com.travel.travel_booking_service.dto.request.BookingRequest;
import com.travel.travel_booking_service.dto.response.BookingResponse;
import com.travel.travel_booking_service.dto.response.BookingStatisticResponse;
import com.travel.travel_booking_service.dto.response.CustomerViewBookingResponse;

public interface BookingService {
    List<BookingResponse> getAllBookings();

    String createPendingBooking(BookingRequest bookingRequest);

    void updateBookingStatus(Long id, String status, String transactionId);

    CustomerViewBookingResponse getBookingById(Long id);

    BookingResponse getBookingDetail(Long id);

    BookingStatisticResponse getBookingStatistics();
}
