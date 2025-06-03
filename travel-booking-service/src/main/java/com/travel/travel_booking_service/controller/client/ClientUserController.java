package com.travel.travel_booking_service.controller.client;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travel.travel_booking_service.dto.request.UserRequest;
import com.travel.travel_booking_service.dto.response.ApiResponse;
import com.travel.travel_booking_service.dto.response.UserResponse;
import com.travel.travel_booking_service.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClientUserController {
    //
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@RequestBody @Valid UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<UserResponse>builder()
                        .data(userService.registerAccount(userRequest))
                        .build());
    }

    //    Lấy thông tin người dùng đăng nhập
    //    @GetMapping("/myInfo")
    //        public ApiResponse<UserResponse> getMyInfo() {
    //            return ApiResponse.<UserResponse>builder()
    //                    .result(userService.getMyInfo())
    //                    .build();
    // => service
    // public UserResponse getMyInfo() {
    //        // Lấy thông tin người dùng hiện tại từ SecurityContext
    //        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //        String username = authentication.getName(); // Lấy username
    //
    //        UserEntity userEntity =
    //                userRepository.findByUsername(username).orElseThrow(() -> new
    // AppException(ErrorCode.USER_NOT_EXISTS));
    //        return userMapper.toUserResponse(userEntity);
    //    }
    //        }
    // Cập nhật thông tin người dùng (chỉ ADMIN hoặc chính người dùng, kiểm tra tham số)
    // @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.id")
    // @PutMapping("/{userId}")
    // public UserResponse updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
    //    return userService.updateUser(userId, request);
    // }

    //    @PostAuthorize("returnObject.username == authentication.name")
    //    public UserResponse getUser(String id){
    //        return ....
    //    }

    //    @GetMapping("/{id}")
    //    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
    //        return ResponseEntity.status(HttpStatus.OK).body(
    //                ApiResponse.<UserResponse>builder()
    //                        .data(userService.getUserById(id))
    //                        .build()
    //        );
    //    }
    //
    //    @PutMapping("/{id}")
    //    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable Long id,
    //            @RequestBody @Valid UserRequest request) {
    //        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<UserResponse>builder()
    //                .data(userService.updateUser(id, request))
    //                .build());
    //    }
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
