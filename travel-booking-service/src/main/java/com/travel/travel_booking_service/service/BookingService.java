package com.travel.travel_booking_service.service;

import java.util.List;

import com.travel.travel_booking_service.dto.request.BookingRequest;
import com.travel.travel_booking_service.dto.response.BookingResponse;

public interface BookingService {
    BookingResponse createBooking(BookingRequest request);

    List<BookingResponse> getAllBookings();

    BookingResponse getBookingById(Long id);

    BookingResponse updateBooking(Long id, BookingRequest request);

    void deleteBooking(Long id);

    List<BookingResponse> searchBookings(String keyword);

    List<BookingResponse> getBookingsByUserId(Long userId);

    List<BookingResponse> getBookingsByTourId(Long tourId);
}
