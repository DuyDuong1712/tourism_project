package com.travel.travel_booking_service.service;

import java.util.List;

import com.travel.travel_booking_service.dto.request.UserCreationRequest;
import com.travel.travel_booking_service.dto.request.UserRequest;
import com.travel.travel_booking_service.dto.request.UserUpdateRequest;
import com.travel.travel_booking_service.dto.response.UserResponse;

public interface UserService {
    UserResponse createUser(UserCreationRequest request);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    UserResponse updateUser(Long id, UserUpdateRequest request);

    void deleteUser(Long id);

    List<UserResponse> searchUsers(String keyword);

    UserResponse getUserByEmail(String email);

    UserResponse getUserByUsername(String username);

    UserResponse registerAccount(UserRequest request);
}
