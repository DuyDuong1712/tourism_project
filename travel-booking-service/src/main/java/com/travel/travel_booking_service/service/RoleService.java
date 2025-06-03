package com.travel.travel_booking_service.service;

import java.util.List;

import com.travel.travel_booking_service.dto.request.PermissionIdsRequest;
import com.travel.travel_booking_service.dto.request.RoleRequest;
import com.travel.travel_booking_service.dto.request.StatusRequest;
import com.travel.travel_booking_service.dto.response.RoleResponse;

public interface RoleService {
    RoleResponse createRole(RoleRequest request);

    List<RoleResponse> getAllRoles();

    RoleResponse getRoleById(Long id);

    RoleResponse updateRole(Long id, RoleRequest request);

    void deactivateRole(Long id);

    void deleteSoftRole(Long id);

    //    Them permissison trong role
    RoleResponse addRolePermissions(Long roleId, PermissionIdsRequest request);

    // Xoa perrmission khoi role
    RoleResponse removePermissionFromRole(Long roleId, PermissionIdsRequest request);

    RoleResponse changeRoleStatus(Long id, StatusRequest request);
}
