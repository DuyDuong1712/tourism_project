package com.travel.travel_booking_service.controller.admin;

import java.util.List;

import com.travel.travel_booking_service.dto.response.BookingStatisticResponse;
import com.travel.travel_booking_service.dto.response.StatisticResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travel.travel_booking_service.dto.response.ApiResponse;
import com.travel.travel_booking_service.dto.response.BookingResponse;
import com.travel.travel_booking_service.service.BookingService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/admin/bookings")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminBookingController {

    BookingService bookingService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getAllBookings() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<BookingResponse>>builder()
                        .data(bookingService.getAllBookings())
                        .build());
    }

        @GetMapping("/{id}")
        public ResponseEntity<ApiResponse<BookingResponse>> getBookingById(@PathVariable Long id) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    ApiResponse.<BookingResponse>builder()
                            .data(bookingService.getBookingDetail(id))
                            .build()
            );
        }


    @GetMapping("/statistics")
    public ResponseEntity<ApiResponse<BookingStatisticResponse>> getBookingStatistics() {
        BookingStatisticResponse statistics = bookingService.getBookingStatistics();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<BookingStatisticResponse>builder().data(statistics).build());
    }



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
