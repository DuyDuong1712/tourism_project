package com.travel.travel_booking_service.controller;

import com.travel.travel_booking_service.dto.response.BookingResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travel.travel_booking_service.dto.request.UpdateProfileRequest;
import com.travel.travel_booking_service.dto.response.ApiResponse;
import com.travel.travel_booking_service.dto.response.CustomerInfoResponse;
import com.travel.travel_booking_service.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @GetMapping("/profile")
    //    @PreAuthorize("isAuthenticated()") // Chỉ cho phép user đã đăng nhập
    public ResponseEntity<ApiResponse<CustomerInfoResponse>> getProfile() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<CustomerInfoResponse>builder()
                        .data(userService.getProfile())
                        .build());
    }

    @PatchMapping()
    public ResponseEntity<ApiResponse<CustomerInfoResponse>> updateProfile(
            @RequestBody UpdateProfileRequest updateProfileRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<CustomerInfoResponse>builder()
                        .data(userService.updateProfile(updateProfileRequest))
                        .build());
    }
}
