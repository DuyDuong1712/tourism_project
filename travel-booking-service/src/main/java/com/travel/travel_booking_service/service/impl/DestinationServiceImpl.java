package com.travel.travel_booking_service.service.impl;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.travel.travel_booking_service.dto.request.StatusRequest;
import com.travel.travel_booking_service.dto.response.CloudinaryUploadResponse;
import com.travel.travel_booking_service.dto.response.DestinationResponse;
import com.travel.travel_booking_service.dto.response.StatisticResponse;
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
            String name, String code, String description, Long parentId, MultipartFile imageFile) {
        if (parentId != null && !destinationRepository.existsById(parentId)) {
            throw new AppException(ErrorCode.DESTINATION_NOT_FOUND);
        }

        if (name == null || name.trim().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_DESTINATION_NAME);
        }
        if (code == null || code.trim().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_DESTINATION_CODE);
        }

        // Check for existing destination with same name or code
        if (destinationRepository.existsByNameIgnoreCase(name.trim())) {
            throw new AppException(ErrorCode.DESTINATION_EXISTS);
        }
        if (destinationRepository.existsByCodeIgnoreCase(code.trim())) {
            throw new AppException(ErrorCode.DESTINATION_CODE_EXISTS);
        }

        // Create destination entity
        Destination destination = Destination.builder()
                .parentId(parentId)
                .name(name.trim())
                .code(code.trim())
                .description(description.trim())
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
            Long id, String name, String code, String description, Long parentId, MultipartFile imageFile) {
        if (parentId != null && !destinationRepository.existsById(parentId)) {
            throw new AppException(ErrorCode.DESTINATION_NOT_FOUND);
        }

        // Lấy destination theo id
        Destination destination =
                destinationRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.DESTINATION_NOT_FOUND));

        if (name == null || name.trim().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_DESTINATION_NAME);
        }

        if (code == null || code.trim().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_DESTINATION_CODE);
        }

        // Kiểm tra trùng tên hoặc code với các destination khác (không tính bản ghi hiện tại)
        if (destinationRepository.existsByNameIgnoreCaseAndIdNot(name.trim(), id)) {
            throw new AppException(ErrorCode.DESTINATION_EXISTS);
        }
        if (destinationRepository.existsByCodeIgnoreCaseAndIdNot(code.trim(), id)) {
            throw new AppException(ErrorCode.DESTINATION_CODE_EXISTS);
        }

        if (parentId != null && parentId.equals(id)) {
            throw new AppException(ErrorCode.INVALID_PARENT_DESTINATION);
        }

        // Cập nhật thông tin
        destination.setParentId(parentId);
        destination.setName(name.trim());
        destination.setCode(code.trim());
        destination.setDescription(description.trim());

        // Xử lý upload ảnh nếu có
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                CloudinaryUploadResponse uploaded = uploadImageFile.upLoadImage(imageFile);
                destination.setImage(uploaded.getUrl());
            } catch (IOException e) {
                throw new AppException(ErrorCode.IMAGE_UPLOAD_FAILED);
            }
        }

        // Lưu và trả về kết quả
        destinationRepository.save(destination);
        return destinationMapper.toDestinationResponse(destination);
    }

    @Override
    public void deleteDestination(Long id) {
        Destination destination =
                destinationRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.DESTINATION_NOT_FOUND));

        if (destination.getTours() != null && !destination.getTours().isEmpty()) {
            throw new AppException(ErrorCode.DESTINATION_IN_USE);
        }

        boolean hasChildren = destinationRepository.existsByParentId(id);
        if (hasChildren) {
            throw new AppException(ErrorCode.DESTINATION_HAS_CHILDREN);
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

        boolean hasChildren = destinationRepository.existsByParentId(id);
        if (hasChildren) {
            throw new AppException(ErrorCode.DESTINATION_HAS_CHILDREN);
        }

        destination.setInActive(statusRequest.getInActive());

        return destinationMapper.toDestinationResponse(destinationRepository.save(destination));
    }

    @Override
    public List<DestinationResponse> getParentDestinations() {
        List<Destination> destinations = destinationRepository.findParentDestinations();
        return destinations.stream()
                .map(destinationMapper::toDestinationResponse)
                .collect(Collectors.toList());
    }

    //    @Override
    //    public List<DestinationResponse> getChildrenByParentId(Long id) {
    //        // Kiểm tra xem parentId có hợp lệ không
    //        if (id == null || !destinationRepository.existsById(id)) {
    //            return Collections.emptyList(); // Trả về danh sách rỗng nếu parentId không hợp lệ
    //        }
    //
    //        // Lấy danh sách destination con theo parentId
    //        List<Destination> destinations = destinationRepository.findByParentId(id);
    //
    //        // Nếu có destination con, chuyển đổi sang DestinationResponse
    //        if (!destinations.isEmpty()) {
    //            return destinations.stream()
    //                    .map(destinationMapper::toDestinationResponse)
    //                    .collect(Collectors.toList());
    //        }
    //
    //        // Nếu không có destination con, trả về chính destination tương ứng với parentId
    //        Optional<Destination> parentDestination = destinationRepository.findById(id);
    //        if (parentDestination.isPresent()) {
    //            return List.of(destinationMapper.toDestinationResponse(parentDestination.get()));
    //        }
    //
    //        // Trường hợp không tìm thấy parentDestination (dù đã kiểm tra existsById)
    //        return Collections.emptyList();
    //    }

    @Override
    public List<DestinationResponse> getChildrenByParentId(Long id) {
        // Kiểm tra parentId hợp lệ
        if (id == null || !destinationRepository.existsById(id)) {
            return Collections.emptyList();
        }

        // Lấy danh sách tất cả các destination là leaf nodes
        List<Destination> leafDestinations = getAllLeafDescendants(id);

        // Nếu không có leaf descendants, kiểm tra xem chính destination có phải là leaf không
        if (leafDestinations.isEmpty()) {
            Optional<Destination> parentDestination = destinationRepository.findById(id);
            if (parentDestination.isPresent() && !destinationRepository.existsByParentId(id)) {
                return List.of(destinationMapper.toDestinationResponse(parentDestination.get()));
            }
            return Collections.emptyList();
        }

        // Chuyển đổi danh sách leaf destinations sang DestinationResponse
        return leafDestinations.stream()
                .map(destinationMapper::toDestinationResponse)
                .collect(Collectors.toList());
    }

    private List<Destination> getAllLeafDescendants(Long parentId) {
        List<Destination> result = new ArrayList<>();
        Set<Long> leafIds = new HashSet<>();

        // Lấy tất cả descendants (bao gồm con, cháu,...)
        List<Destination> descendants = getAllDescendants(parentId);

        // Lọc các destination là leaf (không có con)
        for (Destination dest : descendants) {
            if (!destinationRepository.existsByParentId(dest.getId())) {
                leafIds.add(dest.getId());
            }
        }

        // Chuyển các destination có ID trong leafIds vào kết quả
        for (Destination dest : descendants) {
            if (leafIds.contains(dest.getId())) {
                result.add(dest);
            }
        }

        return result;
    }

    private List<Destination> getAllDescendants(Long parentId) {
        List<Destination> result = new ArrayList<>();
        List<Destination> children = destinationRepository.findByParentId(parentId);

        // Duyệt đệ quy để lấy tất cả descendants
        for (Destination child : children) {
            result.add(child);
            result.addAll(getAllDescendants(child.getId()));
        }

        return result;
    }

    @Override
    public List<DestinationResponse> getAllActiveDestinations() {
        return destinationRepository.findByInActiveTrue().stream()
                .map(destinationMapper::toDestinationResponse)
                .collect(Collectors.toList());
    }

    @Override
    public StatisticResponse getDestinationStatistics() {
        Long activeCount = destinationRepository.countByInActiveTrue();
        Long inActiveCount = destinationRepository.countByInActiveFalse();

        return StatisticResponse.builder()
                .active(activeCount)
                .inactive(inActiveCount)
                .build();
    }

    @Override
    public DestinationResponse getDestinationById(Long id) {
        return null;
    }
}
