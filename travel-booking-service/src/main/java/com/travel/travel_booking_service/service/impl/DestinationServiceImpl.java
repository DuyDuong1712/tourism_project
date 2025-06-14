package com.travel.travel_booking_service.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.travel.travel_booking_service.dto.request.StatusRequest;
import com.travel.travel_booking_service.dto.response.CloudinaryUploadResponse;
import com.travel.travel_booking_service.dto.response.DestinationResponse;
import com.travel.travel_booking_service.entity.Destination;
import com.travel.travel_booking_service.enums.ErrorCode;
import com.travel.travel_booking_service.exception.AppException;
import com.travel.travel_booking_service.mapper.DestinationMapper;
import com.travel.travel_booking_service.repository.DestinationRepository;
import com.travel.travel_booking_service.service.DestinationService;
import com.travel.travel_booking_service.service.UploadImageFile;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DestinationServiceImpl implements DestinationService {

    DestinationRepository destinationRepository;
    DestinationMapper destinationMapper;
    UploadImageFile uploadImageFile;

    // ---ADMIN---
    @Override
    public DestinationResponse createDestination(
            String name, String code, String description, MultipartFile imageFile) {
        // Validate required fields
        if (name == null || name.trim().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_DESTINATION_NAME);
        }
        if (code == null || code.trim().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_DESTINATION_CODE);
        }

        // Trim and validate name and code
        String trimmedName = name.trim();
        String trimmedCode = code.trim();

        // Check for existing destination with same name or code
        if (destinationRepository.existsByNameIgnoreCase(trimmedName)) {
            throw new AppException(ErrorCode.DESTINATION_EXISTS);
        }
        if (destinationRepository.existsByCodeIgnoreCase(trimmedCode)) {
            throw new AppException(ErrorCode.DESTINATION_CODE_EXISTS);
        }

        // Create destination entity
        Destination destination = Destination.builder()
                .name(trimmedName)
                .code(trimmedCode)
                .description(description)
                .build();
        // Handle image upload
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                CloudinaryUploadResponse cloudinaryUploadResponse = uploadImageFile.upLoadImage(imageFile);
                destination.setImage(cloudinaryUploadResponse.getUrl());

            } catch (IOException e) {
                throw new AppException(ErrorCode.IMAGE_UPLOAD_FAILED);
            }
        }

        // Save and return
        destinationRepository.save(destination);
        return destinationMapper.toDestinationResponse(destination);
    }

    @Override
    public DestinationResponse updateDestination(
            Long id, String name, String code, String description, MultipartFile imageFile) {
        // Lấy destination theo id
        Destination destination =
                destinationRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.DESTINATION_NOT_FOUND));

        // Validate required fields
        if (name == null || name.trim().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_DESTINATION_NAME);
        }
        if (code == null || code.trim().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_DESTINATION_CODE);
        }

        String trimmedName = name.trim();
        String trimmedCode = code.trim();

        // Kiểm tra trùng tên (bỏ qua bản ghi hiện tại)
        if (destinationRepository.existsByNameIgnoreCaseAndIdNot(trimmedName, id)) {
            throw new AppException(ErrorCode.DESTINATION_EXISTS);
        }

        // Kiểm tra trùng code (bỏ qua bản ghi hiện tại)
        if (destinationRepository.existsByCodeIgnoreCaseAndIdNot(trimmedCode, id)) {
            throw new AppException(ErrorCode.DESTINATION_CODE_EXISTS);
        }

        // Cập nhật thông tin cho destination
        destination.setName(trimmedName);
        destination.setCode(trimmedCode);
        destination.setDescription(description);

        // Xử lý upload ảnh nếu có
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                CloudinaryUploadResponse cloudinaryUploadResponse = uploadImageFile.upLoadImage(imageFile);
                destination.setImage(cloudinaryUploadResponse.getUrl());
            } catch (IOException e) {
                throw new AppException(ErrorCode.IMAGE_UPLOAD_FAILED);
            }
        }

        // Lưu lại
        destinationRepository.save(destination);

        // Trả về response
        return destinationMapper.toDestinationResponse(destination);
    }

    @Override
    public void deleteDestination(Long id) {
        Destination destination =
                destinationRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.DESTINATION_NOT_FOUND));

        if (destination.getTours() != null && !destination.getTours().isEmpty()) {
            throw new AppException(ErrorCode.DESTINATION_IN_USE);
        }

        destinationRepository.delete(destination);
    }

    @Override
    public List<DestinationResponse> getAllDestinations() {
        return destinationRepository.findAll().stream()
                .map(destinationMapper::toDestinationResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DestinationResponse changeDestinationStatus(Long id, StatusRequest statusRequest) {
        Destination destination =
                destinationRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.DESTINATION_NOT_FOUND));
        destination.setInActive(statusRequest.getInActive());

        return destinationMapper.toDestinationResponse(destinationRepository.save(destination));
    }

    @Override
    public DestinationResponse getDestinationById(Long id) {
        return null;
    }
}
