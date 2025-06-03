package com.travel.travel_booking_service.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.travel_booking_service.dto.request.PermissionRequest;
import com.travel.travel_booking_service.dto.response.PermissionResponse;
import com.travel.travel_booking_service.entity.Permission;
import com.travel.travel_booking_service.enums.ErrorCode;
import com.travel.travel_booking_service.exception.AppException;
import com.travel.travel_booking_service.mapper.PermissionMapper;
import com.travel.travel_booking_service.repository.PermissionRepository;
import com.travel.travel_booking_service.repository.RolePermissionRepository;
import com.travel.travel_booking_service.repository.RoleRepository;
import com.travel.travel_booking_service.service.PermissionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionServiceImpl implements PermissionService {

    PermissionRepository permissionRepository;
    RoleRepository roleRepository;
    RolePermissionRepository rolePermissionRepository;
    PermissionMapper permissionMapper;

    @Override
    @Transactional
    public PermissionResponse addPermission(PermissionRequest request) {
        if (permissionRepository.existsByCodeIgnoreCase(request.getCode())) {
            throw new AppException(ErrorCode.PERMISSION_EXISTS);
        }

        Permission permission = permissionMapper.toPermissionEntity(request);
        permissionRepository.save(permission);

        return permissionMapper.toPermissionResponse(permission);
    }

    @Override
    public List<PermissionResponse> getAllPermissions() {
        List<Permission> permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PermissionResponse updatePermission(Long id, PermissionRequest request) {
        Permission existingPermission =
                permissionRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));

        validatePermission(existingPermission);

        if (!existingPermission.getCode().equals(request.getCode().trim())
                && permissionRepository.existsByCodeIgnoreCase(request.getCode().trim())) {
            throw new AppException(ErrorCode.PERMISSION_EXISTS);
        }

        permissionMapper.updatePermission(existingPermission, request);
        permissionRepository.save(existingPermission);

        return permissionMapper.toPermissionResponse(existingPermission);
    }

    @Override
    public void deactivatePermission(Long id) {
        Permission permission =
                permissionRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
        if (permission.getRolePermissions() != null
                && !permission.getRolePermissions().isEmpty()) {
            throw new AppException(ErrorCode.PERMISSION_IN_USE);
        }

        permission.setInActive(false);

        permissionRepository.save(permission);
    }

    @Override
    @Transactional
    public void deleteSoftPermission(Long id) {
        Permission permission =
                permissionRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));

        if (permission.getRolePermissions() != null
                && !permission.getRolePermissions().isEmpty()) {
            throw new AppException(ErrorCode.PERMISSION_IN_USE);
        }

        permission.setInActive(false);
        permissionRepository.save(permission);
    }

    @Override
    public PermissionResponse getPermissionById(Long id) {
        Permission permission =
                permissionRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
        validatePermission(permission);

        return permissionMapper.toPermissionResponse(permission);
    }

    @Override
    public PermissionResponse getPermissionByCode(String code) {
        Permission permission = permissionRepository
                .findByCodeIgnoreCase(code)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
        validatePermission(permission);

        return permissionMapper.toPermissionResponse(permission);
    }

    //    @Override
    //    public Page<PermissionResponse> getAllPermissions(Pageable pageable) {
    //        return permissionRepository.findAll(pageable);
    //    }

    @Override
    public List<PermissionResponse> searchPermissionsByName(String name) {
        //        List<Permission> permissions =
        // permissionRepository.findAllByNameContainingIgnoreCaseAndInActiveAndIsDelete(name, 1, 0);
        //        return permissions.stream().map(permissionMapper::toPermissionResponse).collect(Collectors.toList());
        return null;
    }

    @Override
    public List<PermissionResponse> getPermissionsByCodes(Set<String> codes) {
        //        List<Permission> permissions = permissionRepository.findAllByCodeInAndInActiveAndIsDelete(codes, 1,
        // 0);
        //        return permissions.stream().map(permissionMapper::toPermissionResponse).collect(Collectors.toList());
        return null;
    }

    @Override
    public void softDeleteMultiple(List<Long> ids) {
        //        List<Permission> permissions = permissionRepository.findByIdInAndInActiveAndIsDelete(ids, 1, 0);
        //        permissions.forEach(permission -> {
        //            permission.setIsDelete(1);
        //            permission.setInActive(0);
        //        });
        //
        //        permissionRepository.saveAll(permissions);
    }

    public void validatePermission(Permission permission) {
        if (!permission.getInActive()) {
            throw new AppException(ErrorCode.PERMISSION_NOT_FOUND);
        }
    }
}
