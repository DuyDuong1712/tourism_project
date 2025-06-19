package com.travel.travel_booking_service.controller;

import com.travel.travel_booking_service.dto.request.CancelBookingRequest;
import com.travel.travel_booking_service.dto.response.BookingResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travel.travel_booking_service.dto.request.BookingRequest;
import com.travel.travel_booking_service.dto.request.UpdateBookinngStatusRequest;
import com.travel.travel_booking_service.dto.response.ApiResponse;
import com.travel.travel_booking_service.dto.response.CustomerViewBookingResponse;
import com.travel.travel_booking_service.service.BookingService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingController {

    BookingService bookingService;

    @PostMapping("/pending")
    public ResponseEntity<String> createPendingBooking(@RequestBody BookingRequest bookingRequest) {
        try {
            String bookingId = bookingService.createPendingBooking(bookingRequest);
            return ResponseEntity.ok(bookingId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating booking: " + e.getMessage());
        }
    }

    @PostMapping("/update-payment-status")
    public ResponseEntity<ApiResponse> updateBookingStatus(@RequestBody UpdateBookinngStatusRequest request) {
        try {
            // Ở đây bạn phải cập nhật trạng thái thanh toán của đơn hàng chứ KHÔNG phải tạo đơn hàng mới.
            bookingService.updateBookingStatus(
                    Long.parseLong(request.getBookingId()),
                    request.getStatus(),
                    request.getTransactionNo()); // ví dụ gọi service xử lý cập nhật
            return ResponseEntity.ok(
                    ApiResponse.builder().data("Cập nhật trạng thái thành công").build());
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.builder()
                            .data("Cập nhật trạng thái thất bại")
                            .build());
        }
    }

    @GetMapping("{bookingId}")
    public ResponseEntity<ApiResponse<CustomerViewBookingResponse>> getBooking(@PathVariable String bookingId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<CustomerViewBookingResponse>builder()
                        .data(bookingService.getBookingById(Long.parseLong(bookingId)))
                        .build());
    }


    @GetMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getBookings(@PathVariable("userId") Long userId, @RequestParam(required = false) String status) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<BookingResponse>>builder()
                        .data(bookingService.getBookingsByUserId(userId, status))
                        .build());
    }

    // Huy tour phia nguoi dung
    @PatchMapping("/{bookingId}/cancel")
    public ResponseEntity cancelBooking(@PathVariable Long bookingId, @RequestBody CancelBookingRequest cancelBookingRequest) {
        bookingService.cancelBooking(bookingId, cancelBookingRequest);
        return ResponseEntity.ok("Booking cancelled successfully");
    }
}
