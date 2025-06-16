package com.travel.travel_booking_service.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import com.travel.travel_booking_service.dto.response.BookingResponse;
import com.travel.travel_booking_service.entity.Booking;
import com.travel.travel_booking_service.repository.BookingRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.travel.travel_booking_service.dto.request.StatusRequest;
import com.travel.travel_booking_service.dto.request.UpdateProfileRequest;
import com.travel.travel_booking_service.dto.request.UserRequest;
import com.travel.travel_booking_service.dto.response.CloudinaryUploadResponse;
import com.travel.travel_booking_service.dto.response.CustomerInfoResponse;
import com.travel.travel_booking_service.dto.response.UserResponse;
import com.travel.travel_booking_service.entity.CustomerInfo;
import com.travel.travel_booking_service.entity.Role;
import com.travel.travel_booking_service.entity.User;
import com.travel.travel_booking_service.enums.ErrorCode;
import com.travel.travel_booking_service.enums.RoleEnum;
import com.travel.travel_booking_service.exception.AppException;
import com.travel.travel_booking_service.mapper.RoleMapper;
import com.travel.travel_booking_service.mapper.UserMapper;
import com.travel.travel_booking_service.repository.CustomerInfoRepository;
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
    CustomerInfoRepository customerInfoRepository;
    BookingRepository bookingRepository;
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
                        .date_of_birth(
                                user.getCustomerInfo().getDateOfBirth() != null
                                        ? user.getCustomerInfo()
                                                .getDateOfBirth()
                                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                                        : null)
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
    public CustomerInfoResponse updateProfile(UpdateProfileRequest updateProfileRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User currentUser =
                userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Lấy hoặc tạo mới CustomerInfo
        CustomerInfo customerInfo = customerInfoRepository
                .findByUser(currentUser)
                .orElseGet(() -> {
                    CustomerInfo newCustomerInfo = new CustomerInfo();
                    newCustomerInfo.setUser(currentUser);
                    return customerInfoRepository.save(newCustomerInfo);
                });

        // Cập nhật các trường nếu có
        if (updateProfileRequest.getFullName() != null
                && !updateProfileRequest.getFullName().trim().isEmpty()) {
            currentUser.setFullname(updateProfileRequest.getFullName().trim());
        }

        if (updateProfileRequest.getPhoneNumber() != null
                && !updateProfileRequest.getPhoneNumber().trim().isEmpty()) {
            if (!updateProfileRequest.getPhoneNumber().matches("\\d{10}")) {
                throw new IllegalArgumentException("Số điện thoại không hợp lệ");
            }
            currentUser.setPhone(updateProfileRequest.getPhoneNumber());
        }

        if (updateProfileRequest.getDate_of_birth() != null
                && !updateProfileRequest.getDate_of_birth().trim().isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate parsedDate = LocalDate.parse(updateProfileRequest.getDate_of_birth(), formatter);
                customerInfo.setDateOfBirth(parsedDate.atStartOfDay()); // dùng LocalDateTime
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Định dạng ngày sinh không hợp lệ (yyyy-MM-dd)");
            }
        }

        if (updateProfileRequest.getGender() != null
                && !updateProfileRequest.getGender().trim().isEmpty()) {
            if (!updateProfileRequest.getGender().equalsIgnoreCase("NAM")
                    && !updateProfileRequest.getGender().equalsIgnoreCase("NỮ")) {
                throw new IllegalArgumentException("Giới tính không hợp lệ (MALE hoặc FEMALE)");
            }
            customerInfo.setGender(updateProfileRequest.getGender().toUpperCase());
        }

        if (updateProfileRequest.getId_card() != null
                && !updateProfileRequest.getId_card().trim().isEmpty()) {
            if (!updateProfileRequest.getId_card().matches("\\d{12}")) {
                throw new IllegalArgumentException("CMND/CCCD không hợp lệ");
            }
            customerInfo.setIdCard(updateProfileRequest.getId_card());
        }

        if (updateProfileRequest.getPassport() != null
                && !updateProfileRequest.getPassport().trim().isEmpty()) {
            if (!updateProfileRequest.getPassport().matches("[A-Z0-9]{8}")) {
                throw new IllegalArgumentException("Hộ chiếu không hợp lệ");
            }
            customerInfo.setPassport(updateProfileRequest.getPassport());
        }

        if (updateProfileRequest.getAddress() != null
                && !updateProfileRequest.getAddress().trim().isEmpty()) {
            customerInfo.setAddress(updateProfileRequest.getAddress().trim());
        }

        if (updateProfileRequest.getCountry() != null
                && !updateProfileRequest.getCountry().trim().isEmpty()) {
            customerInfo.setCountry(updateProfileRequest.getCountry().trim());
        }

        // Gán lại nếu user chưa gán customerInfo
        if (currentUser.getCustomerInfo() == null) {
            currentUser.setCustomerInfo(customerInfo);
        }

        // Lưu thông tin
        userRepository.save(currentUser); // do cascade nên customerInfo cũng sẽ được lưu

        return CustomerInfoResponse.builder()
                .fullname(currentUser.getFullname())
                .address(customerInfo.getAddress())
                .phone(currentUser.getPhone())
                .date_of_birth(
                        customerInfo.getDateOfBirth() != null
                                ? customerInfo.getDateOfBirth().toLocalDate().toString()
                                : null)
                .gender(customerInfo.getGender())
                .id_card(customerInfo.getIdCard())
                .passport(customerInfo.getPassport())
                .country(customerInfo.getCountry())
                .build();
    }

    @Override
    public List<BookingResponse> getBookingsByUserId(Long id) {
        List<Booking> bookings = bookingRepository.findByCustomer_Id(id);
        List<BookingResponse> bookingResponses = new ArrayList<>();
        if (bookings != null && !bookings.isEmpty()) {
            for (Booking booking : bookings) {
                BookingResponse bookingResponse = BookingResponse.builder()
                        .id(booking.getId())
                        .tourDetailId(booking.getTourDetail().getId())
                        .tourName(booking.getTourDetail().getTour().getTitle())
                        .customerId(booking.getCustomer().getId())
                        .fullName(booking.getFullName())
                        .email(booking.getEmail())
                        .phoneNumber(booking.getPhoneNumber())
                        .address(booking.getAddress())
                        .adultCount(booking.getAdultCount())
                        .childrenCount(booking.getChildrenCount())
                        .childCount(booking.getChildCount())
                        .babyCount(booking.getBabyCount())
                        .totalPeople(booking.getTotalPeople())
                        .singleRoomCount(booking.getSingleRoomCount())
                        .subtotal(booking.getSubtotal())
                        .discountAmount(booking.getDiscountAmount())
                        .totalAmount(booking.getTotalAmount())
                        .note(booking.getNote())
                        .bookingStatus(booking.getBookingStatus())
                        .paymentStatus(booking.getPaymentStatus())
                        .confirmedAt(booking.getConfirmedAt())
                        .cancelledAt(booking.getCancelledAt())
                        .cancellationReason(booking.getCancellationReason())
                        .cancelledBy(booking.getCancelledBy())
                        .transactionId(booking.getTransactionId())
                        .paymentDate(booking.getPaymentDate())
                        .paymentDescription(booking.getPaymentDescription())
                        .refundAmount(booking.getRefundAmount())
                        .refundPercent(booking.getRefundPercent())
                        .refundDate(booking.getRefundDate())
                        .refundStatus(booking.getRefundStatus())
                        .refundTransactionId(booking.getRefundTransactionId())
                        .refundNote(booking.getRefundNote())
                        .createdDate(booking.getCreatedDate())
                        .modifiedDate(booking.getModifiedDate())
                        .createdBy(booking.getCreatedBy())
                        .modifiedBy(booking.getModifiedBy())
                        .startDate(booking.getTourDetail().getDayStart())
                        .returnDate(booking.getTourDetail().getDayReturn())
                        .build();

                bookingResponses.add(bookingResponse);
            }
        }
        return bookingResponses;
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
