package com.travel.travel_booking_service.controller.admin;

import org.springframework.web.bind.annotation.*;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/admin/bookings")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminBookingController {

    //    BookingService bookingService;
    //
    //    @GetMapping
    //    public ResponseEntity<ApiResponse<List<BookingResponse>>> getAllBookings() {
    //        return ResponseEntity.status(HttpStatus.OK).body(
    //                ApiResponse.<List<BookingResponse>>builder()
    //                        .data(bookingService.getAllBookings())
    //                        .build()
    //        );
    //    }
    //
    //    @GetMapping("/{id}")
    //    public ResponseEntity<ApiResponse<BookingResponse>> getBookingById(@PathVariable Long id) {
    //        return ResponseEntity.status(HttpStatus.OK).body(
    //                ApiResponse.<BookingResponse>builder()
    //                        .data(bookingService.getBookingById(id))
    //                        .build()
    //        );
    //    }
    //
    //    @PutMapping("/{id}")
    //    public ResponseEntity<ApiResponse<BookingResponse>> updateBooking(@PathVariable Long id,
    //            @RequestBody @Valid BookingRequest request) {
    //        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<BookingResponse>builder()
    //                .data(bookingService.updateBooking(id, request))
    //                .build());
    //    }
    //
    //    @DeleteMapping("/{id}")
    //    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
    //        bookingService.deleteBooking(id);
    //        return ResponseEntity.noContent().build();
    //    }
    //
    //    @GetMapping("/search")
    //    public ResponseEntity<ApiResponse<List<BookingResponse>>> searchBookings(@RequestParam String keyword) {
    //        return ResponseEntity.status(HttpStatus.OK).body(
    //                ApiResponse.<List<BookingResponse>>builder()
    //                        .data(bookingService.searchBookings(keyword))
    //                        .build()
    //        );
    //    }
    //
    //    @GetMapping("/user/{userId}")
    //    public ResponseEntity<ApiResponse<List<BookingResponse>>> getBookingsByUserId(@PathVariable Long userId) {
    //        return ResponseEntity.status(HttpStatus.OK).body(
    //                ApiResponse.<List<BookingResponse>>builder()
    //                        .data(bookingService.getBookingsByUserId(userId))
    //                        .build()
    //        );
    //    }
    //
    //    @GetMapping("/tour/{tourId}")
    //    public ResponseEntity<ApiResponse<List<BookingResponse>>> getBookingsByTourId(@PathVariable Long tourId) {
    //        return ResponseEntity.status(HttpStatus.OK).body(
    //                ApiResponse.<List<BookingResponse>>builder()
    //                        .data(bookingService.getBookingsByTourId(tourId))
    //                        .build()
    //        );
    //    }
    //
    //    @PutMapping("/{id}/status")
    //    public ResponseEntity<ApiResponse<BookingResponse>> updateBookingStatus(@PathVariable Long id,
    //            @RequestParam String status) {
    //        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<BookingResponse>builder()
    //                .data(bookingService.updateBookingStatus(id, status))
    //                .build());
    //    }
}
