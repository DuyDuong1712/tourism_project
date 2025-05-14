package com.travel.travel_booking_service.service;

import com.travel.travel_booking_service.dto.UserDTO;
import com.travel.travel_booking_service.dto.UserRegistrationDTO;
import com.travel.travel_booking_service.dto.UserUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserDTO registerUser(UserRegistrationDTO registrationDTO);
    UserDTO updateUser(Long id, UserUpdateDTO updateDTO);
    void deleteUser(Long id);
    UserDTO getUserById(Long id);
    UserDTO getUserByEmail(String email);
    UserDTO getUserByUsername(String username);
    Page<UserDTO> getAllUsers(Pageable pageable);
    void changePassword(Long id, String oldPassword, String newPassword);
    void resetPassword(String email);
    void activateUser(Long id);
    void deactivateUser(Long id);
    void updateUserRole(Long id, String role);
    boolean isEmailExists(String email);
    boolean isUsernameExists(String username);
} 