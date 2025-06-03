package com.travel.travel_booking_service.controller.admin;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travel.travel_booking_service.dto.request.UserCreationRequest;
import com.travel.travel_booking_service.dto.request.UserUpdateRequest;
import com.travel.travel_booking_service.dto.response.ApiResponse;
import com.travel.travel_booking_service.dto.response.UserResponse;
import com.travel.travel_booking_service.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminUserController {

    UserService userService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody UserCreationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<UserResponse>builder()
                        .data(userService.createUser(request))
                        .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<UserResponse>>builder()
                        .data(userService.getAllUsers())
                        .build());
    }
    //
    //    @GetMapping("/{id}")
    //    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
    //        return ResponseEntity.status(HttpStatus.OK).body(
    //                ApiResponse.<UserResponse>builder()
    //                        .data(userService.getUserById(id))
    //                        .build()
    //        );
    //    }
    //
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @PathVariable Long id, @RequestBody @Valid UserUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<UserResponse>builder()
                        .data(userService.updateUser(id, request))
                        .build());
    }
    //
    //    @DeleteMapping("/{id}")
    //    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    //        userService.deleteUser(id);
    //        return ResponseEntity.noContent().build();
    //    }
    //
    //    @GetMapping("/search")
    //    public ResponseEntity<ApiResponse<List<UserResponse>>> searchUsers(@RequestParam String keyword) {
    //        return ResponseEntity.status(HttpStatus.OK).body(
    //                ApiResponse.<List<UserResponse>>builder()
    //                        .data(userService.searchUsers(keyword))
    //                        .build()
    //        );
    //    }
    //
    //    @PutMapping("/{id}/status")
    //    public ResponseEntity<ApiResponse<UserResponse>> updateUserStatus(@PathVariable Long id,
    //            @RequestParam boolean active) {
    //        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<UserResponse>builder()
    //                .data(userService.updateUserStatus(id, active))
    //                .build());
    //    }
    //
    //    @PutMapping("/{id}/role")
    //    public ResponseEntity<ApiResponse<UserResponse>> updateUserRole(@PathVariable Long id,
    //            @RequestParam Long roleId) {
    //        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<UserResponse>builder()
    //                .data(userService.updateUserRole(id, roleId))
    //                .build());
    //    }
}
