package com.travel.travel_booking_service.service.impl;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.travel_booking_service.dto.request.PermissionIdsRequest;
import com.travel.travel_booking_service.dto.request.RoleRequest;
import com.travel.travel_booking_service.dto.request.StatusRequest;
import com.travel.travel_booking_service.dto.response.PermissionResponse;
import com.travel.travel_booking_service.dto.response.RoleResponse;
import com.travel.travel_booking_service.entity.Permission;
import com.travel.travel_booking_service.entity.Role;
import com.travel.travel_booking_service.entity.RolePermission;
import com.travel.travel_booking_service.enums.ErrorCode;
import com.travel.travel_booking_service.exception.AppException;
import com.travel.travel_booking_service.mapper.PermissionMapper;
import com.travel.travel_booking_service.mapper.RoleMapper;
import com.travel.travel_booking_service.repository.PermissionRepository;
import com.travel.travel_booking_service.repository.RolePermissionRepository;
import com.travel.travel_booking_service.repository.RoleRepository;
import com.travel.travel_booking_service.service.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImpl implements RoleService {

    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RolePermissionRepository rolePermissionRepository;
    RoleMapper roleMapper;
    PermissionMapper permissionMapper;

    @Override
    public RoleResponse createRole(RoleRequest request) {
        if (roleRepository.existsByCodeIgnoreCase(request.getCode())) {
            throw new AppException(ErrorCode.ROLE_EXISTS);
        }
        Role role = roleMapper.toRoleEntity(request);

        // Lưu role trước để có ID
        role = roleRepository.save(role);

        Set<String> permissionCodes = request.getPermissions();
        List<Permission> permissions = permissionRepository.findByCodeIn(permissionCodes);

        List<RolePermission> rolePermissions = new ArrayList<>();
        for (Permission permission : permissions) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setPermission(permission);
            rolePermission.setRole(role);
            rolePermissions.add(rolePermission);
        }
        rolePermissionRepository.saveAll(rolePermissions);

        role.setRolePermissions(rolePermissions);

        RoleResponse roleResponse = roleMapper.toRoleResponse(role);
        roleResponse.setPermissionResponses(
                permissions.stream().map(permissionMapper::toPermissionResponse).toList());
        return roleResponse;
    }

    @Override
    public List<RoleResponse> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        List<RoleResponse> roleResponses = new ArrayList<>();
        for (Role role : roles) {
            List<RolePermission> rolePermissions = role.getRolePermissions();
            RoleResponse roleResponse = roleMapper.toRoleResponse(role);
            List<Permission> permissions =
                    rolePermissions.stream().map(rl -> rl.getPermission()).toList();
            roleResponse.setPermissionResponses(permissions.stream()
                    .map(permissionMapper::toPermissionResponse)
                    .toList());
            roleResponses.add(roleResponse);
        }
        return roleResponses;
    }

    @Override
    public void deactivateRole(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        if (role.getRolePermissions() != null && !role.getRolePermissions().isEmpty()) {
            throw new AppException(ErrorCode.ROLE_IN_USE);
        }
        role.setInActive(false);

        roleRepository.save(role);
    }

    @Override
    @Transactional
    public void deleteSoftRole(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        if (role.getCode().equalsIgnoreCase("ADMIN")) {
            throw new AppException(ErrorCode.ROLE_ADMIN_CAN_NOT_DELETE);
        }
        roleRepository.delete(role);
    }

    @Override
    public RoleResponse getRoleById(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        RoleResponse roleResponse = roleMapper.toRoleResponse(role);

        List<PermissionResponse> permissionResponses = new ArrayList<>();
        List<RolePermission> rolePermissions = role.getRolePermissions();
        for (RolePermission rolePermission : rolePermissions) {
            permissionResponses.add(permissionMapper.toPermissionResponse(rolePermission.getPermission()));
        }

        roleResponse.setPermissionResponses(permissionResponses);
        return roleResponse;
    }

    @Override
    public RoleResponse addRolePermissions(Long roleId, PermissionIdsRequest request) {
        // Tìm role
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        if (role.getCode().equals("ADMIN")) {
            throw new AppException(ErrorCode.ROLE_ADMIN_PERMISSION_CAN_NOT_CHANGE);
        }

        // Tìm danh sách permission
        List<Permission> permissions = permissionRepository.findAllById(request.getPermissionIds());

        // Kiểm tra nếu thiếu quyền nào đó
        if (permissions.size() != request.getPermissionIds().size()) {
            throw new AppException(ErrorCode.PERMISSION_NOT_FOUND);
        }

        // Danh sách RolePermission mới cần thêm
        List<RolePermission> newRolePermissions = new ArrayList<>();

        for (Permission permission : permissions) {
            boolean exists = rolePermissionRepository.existsByRole_IdAndPermission_Id(roleId, permission.getId());
            if (!exists) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRole(role);
                rolePermission.setPermission(permission);
                newRolePermissions.add(rolePermission);
            }
        }

        // Lưu nếu có RolePermission mới
        if (!newRolePermissions.isEmpty()) {
            rolePermissionRepository.saveAll(newRolePermissions);
            role.getRolePermissions().addAll(newRolePermissions);
            roleRepository.save(role);
        }

        // Trả về RoleResponse kèm danh sách quyền hiện tại
        RoleResponse roleResponse = roleMapper.toRoleResponse(role);

        List<Permission> allPermissions = role.getRolePermissions().stream()
                .map(RolePermission::getPermission)
                .toList();

        roleResponse.setPermissionResponses(allPermissions.stream()
                .map(permissionMapper::toPermissionResponse)
                .toList());

        return roleResponse;
    }

    @Override
    @Transactional
    public RoleResponse removePermissionFromRole(Long roleId, PermissionIdsRequest request) {
        // Tìm role
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        // Tìm danh sách permission
        List<Permission> permissions = permissionRepository.findAllById(request.getPermissionIds());

        if (permissions.isEmpty()) {
            throw new AppException(ErrorCode.PERMISSION_NOT_FOUND);
        }

        // Duyệt qua từng permission và xóa RolePermission nếu tồn tại
        for (Permission permission : permissions) {
            Optional<RolePermission> rolePermissionOpt =
                    rolePermissionRepository.findByRoleAndPermission(role, permission);

            rolePermissionOpt.ifPresent(rolePermission -> {
                rolePermissionRepository.delete(rolePermission);
                // Xóa trong bộ nhớ nếu dùng ánh xạ 2 chiều
                role.getRolePermissions()
                        .removeIf(rp -> rp.getPermission().getId().equals(permission.getId()));
            });
        }

        // Lưu role nếu cần
        roleRepository.save(role);

        // Trả về RoleResponse sau khi cập nhật
        RoleResponse roleResponse = roleMapper.toRoleResponse(role);

        List<Permission> remainingPermissions = role.getRolePermissions().stream()
                .map(RolePermission::getPermission)
                .toList();

        roleResponse.setPermissionResponses(remainingPermissions.stream()
                .map(permissionMapper::toPermissionResponse)
                .toList());

        return roleResponse;
    }

    @Override
    @Transactional
    public RoleResponse updateRole(Long id, RoleRequest request) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        String newCode = request.getCode().trim();

        // Check if the new code is different and already exists
        if (!newCode.equalsIgnoreCase(role.getCode()) && roleRepository.existsByCodeIgnoreCase(newCode)) {
            throw new AppException(ErrorCode.ROLE_EXISTS);
        }

        roleMapper.updateRole(role, request);

        // Xóa các RolePermission cũ và flush để đảm bảo xóa ngay lập tức
        rolePermissionRepository.deleteByRole(role);
        rolePermissionRepository.flush(); // Đảm bảo xóa được thực hiện ngay
        role.getRolePermissions().clear();

        // Lấy danh sách Permission mới
        Set<String> permissionCodes = request.getPermissions();
        List<Permission> permissions = permissionRepository.findByCodeIn(permissionCodes);

        // Tạo và lưu các RolePermission mới
        List<RolePermission> rolePermissions = new ArrayList<>();
        for (Permission permission : permissions) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRole(role);
            rolePermission.setPermission(permission);
            rolePermissions.add(rolePermission);
        }

        // Lưu các RolePermission mới
        if (!rolePermissions.isEmpty()) {
            rolePermissionRepository.saveAll(rolePermissions);
        }

        // Cập nhật relationship trong role entity
        role.setRolePermissions(rolePermissions);

        // Lưu role sau khi cập nhật
        roleRepository.save(role);

        // Tạo và trả về RoleResponse
        RoleResponse roleResponse = roleMapper.toRoleResponse(role);
        roleResponse.setPermissionResponses(
                permissions.stream().map(permissionMapper::toPermissionResponse).toList());

        return roleResponse;
    }

    @Override
    public RoleResponse changeRoleStatus(Long id, StatusRequest request) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        if (role.getCode().equals("ADMIN")) {
            throw new AppException(ErrorCode.ROLE_ADMIN_PERMISSION_CAN_NOT_CHANGE);
        }
        role.setInActive(request.getInActive());

        return roleMapper.toRoleResponse(roleRepository.save(role));
    }
}
