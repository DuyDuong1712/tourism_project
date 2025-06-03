package com.travel.travel_booking_service.service;

import java.util.List;
import java.util.Set;

import com.travel.travel_booking_service.dto.request.PermissionRequest;
import com.travel.travel_booking_service.dto.response.PermissionResponse;

public interface PermissionService {
    PermissionResponse addPermission(PermissionRequest request);

    List<PermissionResponse> getAllPermissions();

    PermissionResponse updatePermission(Long id, PermissionRequest request);

    void deactivatePermission(Long id);

    void deleteSoftPermission(Long id);

    PermissionResponse getPermissionById(Long id);

    PermissionResponse getPermissionByCode(String code);

    //    Page<PermissionResponse> getAllPermissions(Pageable pageable);

    List<PermissionResponse> searchPermissionsByName(String name);

    //    boolean existsByCode(String code);
    //
    // Lấy nhiều quyền từ danh sách code (cho tạo role)
    List<PermissionResponse> getPermissionsByCodes(Set<String> codes);

    void softDeleteMultiple(List<Long> ids);
}
