package com.travel.travel_booking_service.controller;

import com.travel.travel_booking_service.dto.request.BookingRequest;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travel.travel_booking_service.dto.request.TransportRequest;
import com.travel.travel_booking_service.dto.response.ApiResponse;
import com.travel.travel_booking_service.dto.response.TransportResponse;
import com.travel.travel_booking_service.service.BookingService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingController {

    BookingService bookingService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<TransportResponse>> createTransport(
            @RequestBody @Valid TransportRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<TransportResponse>builder()
                        //                        .data(bookingService.createBooking())
                        .build());
    }

    @PostMapping("/pending")
    public ResponseEntity<String> createPendingBooking(@RequestBody BookingRequest bookingRequest) {
        try {
            String bookingId = bookingService.createPendingBooking(bookingRequest);
            return ResponseEntity.ok(bookingId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating booking: " + e.getMessage());
        }
    }
}
