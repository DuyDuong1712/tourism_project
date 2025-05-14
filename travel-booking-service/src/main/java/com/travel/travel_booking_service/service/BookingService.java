package com.travel.travel_booking_service.service;

import com.travel.travel_booking_service.dto.BookingDTO;
import com.travel.travel_booking_service.dto.BookingRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookingService {
    BookingDTO createBooking(BookingRequestDTO bookingRequest);
    BookingDTO updateBooking(Long id, BookingDTO bookingDTO);
    void deleteBooking(Long id);
    BookingDTO getBookingById(Long id);
    Page<BookingDTO> getAllBookings(Pageable pageable);
    Page<BookingDTO> getBookingsByUserId(Long userId, Pageable pageable);
    Page<BookingDTO> getBookingsByTourId(Long tourId, Pageable pageable);
    void cancelBooking(Long id);
    void confirmBooking(Long id);
    void rejectBooking(Long id);
    void processRefund(Long id);
    List<BookingDTO> getUpcomingBookings(Long userId);
    List<BookingDTO> getPastBookings(Long userId);
    boolean isBookingCancellable(Long id);
    void updateBookingStatus(Long id, String status);
} 