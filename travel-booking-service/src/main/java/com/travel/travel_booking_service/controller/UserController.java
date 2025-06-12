package com.travel.travel_booking_service.controller;

import com.travel.travel_booking_service.dto.request.UpdateProfileRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travel.travel_booking_service.dto.response.ApiResponse;
import com.travel.travel_booking_service.dto.response.CustomerInfoResponse;
import com.travel.travel_booking_service.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

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

//    @PatchMapping()
//    public ResponseEntity<ApiResponse<CustomerInfoResponse>> updateProfile(
//            @RequestParam(value = "fullName", required = false) String fullName,
//            @RequestParam(value = "address", required = false) String address,
//            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
//            @RequestParam(value = "date_of_birth", required = false) String date_of_birth,
//            @RequestParam(value = "gender", required = false) String gender,
//            @RequestParam(value = "id_card", required = false) String id_card,
//            @RequestParam(value = "passport", required = false) String passport,
//            @RequestParam(value = "country", required = false) String country
//            ) {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(ApiResponse.<CustomerInfoResponse>builder()
//                        .data(userService.updateProfile(fullName, address, phoneNumber,date_of_birth, gender, id_card, passport, country))
//                        .build());
//    }

    @PatchMapping()
    public ResponseEntity<ApiResponse<CustomerInfoResponse>> updateProfile(
            @RequestBody UpdateProfileRequest updateProfileRequest
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<CustomerInfoResponse>builder()
                        .data(userService.updateProfile(updateProfileRequest))
                        .build());
    }
}
