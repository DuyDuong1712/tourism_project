package com.travel.travel_booking_service.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.travel.travel_booking_service.dto.request.StatusRequest;
import com.travel.travel_booking_service.dto.request.UserRequest;
import com.travel.travel_booking_service.dto.response.CloudinaryUploadResponse;
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

    //    @Override
    //    public UserResponse getUserById(Long id) {
    //        return convertToResponse(userRepository.findById(id)
    //                .orElseThrow(() -> new RuntimeException("User not found")));
    //    }
    //
    //    @Override
    //    @Transactional
    //    public UserResponse updateUser(Long id, UserRequest request) {
    //        User user = userRepository.findById(id)
    //                .orElseThrow(() -> new RuntimeException("User not found"));
    //
    //        user.setUsername(request.getUsername());
    //        user.setEmail(request.getEmail());
    //        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
    //            user.setPassword(passwordEncoder.encode(request.getPassword()));
    //        }
    //        user.setFirstName(request.getFirstName());
    //        user.setLastName(request.getLastName());
    //        user.setPhone(request.getPhone());
    //        user.setAddress(request.getAddress());
    //
    //        return convertToResponse(userRepository.save(user));
    //    }
    //
    //    @Override
    //    @Transactional
    //    public void deleteUser(Long id) {
    //        userRepository.deleteById(id);
    //    }
    //
    //    @Override
    //    public List<UserResponse> searchUsers(String keyword) {
    //        return userRepository.searchUsers(keyword).stream()
    //                .map(this::convertToResponse)
    //                .collect(Collectors.toList());
    //    }
    //
    //    @Override
    //    public UserResponse getUserByEmail(String email) {
    //        return convertToResponse(userRepository.findByEmail(email)
    //                .orElseThrow(() -> new RuntimeException("User not found")));
    //    }
    //
    //    @Override
    //    public UserResponse getUserByUsername(String username) {
    //        return convertToResponse(userRepository.findByUsername(username)
    //                .orElseThrow(() -> new RuntimeException("User not found")));
    //    }
    //
    //    private UserResponse convertToResponse(User user) {
    //        return UserResponse.builder()
    //                .id(user.getId())
    //                .username(user.getUsername())
    //                .email(user.getEmail())
    //                .firstName(user.getFirstName())
    //                .lastName(user.getLastName())
    //                .phone(user.getPhone())
    //                .address(user.getAddress())
    //                .build();
    //    }
}
