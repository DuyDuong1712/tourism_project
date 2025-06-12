package com.travel.travel_booking_service.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.travel.travel_booking_service.dto.request.StatusRequest;
import com.travel.travel_booking_service.dto.request.UserRequest;
import com.travel.travel_booking_service.dto.response.CloudinaryUploadResponse;
import com.travel.travel_booking_service.dto.response.CustomerInfoResponse;
import com.travel.travel_booking_service.dto.response.UserResponse;
import com.travel.travel_booking_service.entity.Role;
import com.travel.travel_booking_service.entity.User;
import com.travel.travel_booking_service.enums.ErrorCode;
import com.travel.travel_booking_service.enums.RoleEnum;
import com.travel.travel_booking_service.exception.AppException;
import com.travel.travel_booking_service.mapper.RoleMapper;
import com.travel.travel_booking_service.mapper.UserMapper;
import com.travel.travel_booking_service.repository.RoleRepository;
import com.travel.travel_booking_service.repository.UserRepository;
import com.travel.travel_booking_service.service.UploadImageFile;
import com.travel.travel_booking_service.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    UploadImageFile uploadImageFile;

    private final RoleMapper roleMapper;

    @Override
    public UserResponse createUser(
            String username,
            String password,
            String fullName,
            String email,
            String phone,
            Long roleId,
            MultipartFile imageFile) {
        if (userRepository.existsByUsername(username) || userRepository.existsByEmail(email)) {
            throw new AppException(ErrorCode.USER_EXISTS);
        }

        User user = User.builder()
                .username(username)
                .fullname(fullName)
                .email(email)
                .phone(phone)
                .build();

        user.setPassword(passwordEncoder.encode(password));

        Role roleEntity = roleRepository.findById(roleId).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        user.setRole(roleEntity);

        // Handle image upload
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                CloudinaryUploadResponse cloudinaryUploadResponse = uploadImageFile.upLoadImage(imageFile);
                user.setProfileImg(cloudinaryUploadResponse.getUrl());

            } catch (IOException e) {
                throw new AppException(ErrorCode.IMAGE_UPLOAD_FAILED);
            }
        }

        userRepository.save(user);

        UserResponse userResponse = userMapper.toUserResponse(user);
        userResponse.setRole(roleEntity.getCode());

        return userResponse;
    }

    @Override
    public UserResponse registerAccount(UserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTS);
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTS);
        }

        if (!request.getPassword().equals(request.getRetypePassword())) {
            throw new AppException(ErrorCode.RETYPEPASSWORD_NOT_MATCH);
        }

        if (!request.getEmail().equals(request.getRetypeEmail())) {
            throw new AppException(ErrorCode.RETYPEMAIL_NOT_MATCH);
        }

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role role = roleRepository
                .findByCode(RoleEnum.CUSTOMER.name())
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        user.setRole(role);

        UserResponse userResponse = userMapper.toUserResponse(userRepository.save(user));
        userResponse.setRole(role.getCode());

        return userResponse;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : users) {
            UserResponse userResponse = userMapper.toUserResponse(user);
            userResponse.setRole(user.getRole().getCode());
            userResponse.setProfileImg(user.getProfileImg());
            userResponses.add(userResponse);
        }

        return userResponses;
    }

    @Override
    public UserResponse updateUser(
            Long id,
            String username,
            String password,
            String fullName,
            String email,
            String phone,
            Long roleid,
            MultipartFile imageFile) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Role role = roleRepository.findById(roleid).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        user.setUsername(username);
        user.setFullname(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setRole(role);

        if (password != null && !password.trim().equals("")) {
            user.setPassword(passwordEncoder.encode(password));
        }

        // Xử lý upload ảnh nếu có
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                CloudinaryUploadResponse uploaded = uploadImageFile.upLoadImage(imageFile);
                user.setProfileImg(uploaded.getUrl());
            } catch (IOException e) {
                throw new AppException(ErrorCode.IMAGE_UPLOAD_FAILED);
            }
        }

        UserResponse userResponse = userMapper.toUserResponse(userRepository.save(user));
        userResponse.setRole(role.getCode());
        return userResponse;
    }

    @Override
    public UserResponse changeStatus(Long id, StatusRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (user.getRole().getCode().equalsIgnoreCase(RoleEnum.ADMIN.name())) {
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }

        user.setInActive(request.getInActive());

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse updateUserRole(Long id, String role) {
        return null;
    }

    @Override
    public UserResponse getUserById(Long id) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (user.getRole().getCode().equalsIgnoreCase(RoleEnum.ADMIN.name())) {
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }
        userRepository.delete(user);
    }

    @Override
    public CustomerInfoResponse getProfile() {
        // Lấy thông tin người dùng hiện tại từ SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            // Lấy thông tin user từ authentication.getPrincipal()
            Object principal = authentication.getPrincipal();
            if (principal instanceof Jwt) {
                Jwt jwt = (Jwt) principal;
                // Lấy thông tin username hoặc các thuộc tính khác
                // Gọi repository hoặc service để lấy thông tin chi tiết của user
                String username = jwt.getSubject(); // hoặc jwt.getClaim("username")

                User user = userRepository
                        .findByUsername(username)
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

                CustomerInfoResponse customerInfoResponse = CustomerInfoResponse.builder()
                        .id(user.getId())
                        .fullname(user.getFullname())
                        .email(user.getEmail())
                        .phone(user.getPhone())
                        .address(user.getCustomerInfo().getAddress())
                        .gender(user.getCustomerInfo().getGender())
                        .date_of_birth(user.getCustomerInfo().getDateOfBirth().toString())
                        .id_card(user.getCustomerInfo().getIdCard())
                        .passport(user.getCustomerInfo().getPassport())
                        .country(user.getCustomerInfo().getCountry())
                        .build();

                return customerInfoResponse;
            }
        }
        throw new RuntimeException("User not authenticated");
    }

    @Override
    public List<UserResponse> searchUsers(String keyword) {
        return List.of();
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        return null;
    }

    @Override
    public UserResponse getUserByUsername(String username) {
        return null;
    }
}
