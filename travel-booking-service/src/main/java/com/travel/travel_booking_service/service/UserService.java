package com.travel.travel_booking_service.service;

import java.util.List;

import com.travel.travel_booking_service.dto.response.BookingResponse;
import org.springframework.web.multipart.MultipartFile;

import com.travel.travel_booking_service.dto.request.StatusRequest;
import com.travel.travel_booking_service.dto.request.UpdateProfileRequest;
import com.travel.travel_booking_service.dto.request.UserRequest;
import com.travel.travel_booking_service.dto.response.CustomerInfoResponse;
import com.travel.travel_booking_service.dto.response.UserResponse;

public interface UserService {
    UserResponse createUser(
            String username,
            String password,
            String fullName,
            String email,
            String phone,
            Long roleId,
            MultipartFile imageFile);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    UserResponse updateUser(
            Long id,
            String username,
            String password,
            String fullName,
            String email,
            String phone,
            Long roleid,
            MultipartFile imageFile);

    void deleteUser(Long id);

    UserResponse changeStatus(Long id, StatusRequest request);

    UserResponse updateUserRole(Long id, String role);

    List<UserResponse> searchUsers(String keyword);

    UserResponse getUserByEmail(String email);

    UserResponse getUserByUsername(String username);

    UserResponse registerAccount(UserRequest request);

    CustomerInfoResponse getProfile();

    //    CustomerInfoResponse updateProfile(String fullName, String address, String phoneNumber, String date_of_birth,
    //                                        String gender,
    //                                        String id_card,
    //                                        String passport,
    //                                        String country
    //                                       );
    CustomerInfoResponse updateProfile(UpdateProfileRequest updateProfileRequest);
}
