package com.travel.travel_booking_service.service;

import java.util.List;

import com.travel.travel_booking_service.dto.request.BookingRequest;
import com.travel.travel_booking_service.dto.response.BookingResponse;

public interface BookingService {
    String createPendingBooking(BookingRequest bookingRequest);

    void updateBookingStatus(Long id, String status);
}
