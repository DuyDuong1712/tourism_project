package com.travel.travel_booking_service.controller.admin;

import java.util.List;

import com.travel.travel_booking_service.dto.request.StatusRequest;
import com.travel.travel_booking_service.dto.response.DestinationResponse;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travel.travel_booking_service.dto.request.PermissionIdsRequest;
import com.travel.travel_booking_service.dto.request.RoleRequest;
import com.travel.travel_booking_service.dto.response.ApiResponse;
import com.travel.travel_booking_service.dto.response.RoleResponse;
import com.travel.travel_booking_service.service.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/admin/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminRoleController {

    RoleService roleService;

    //    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<RoleResponse>> createRole(@RequestBody @Valid RoleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<RoleResponse>builder()
                        .data(roleService.createRole(request))
                        .build());
    }

    //    @PreAuthorize("hasAuthority()")
    @GetMapping
    public ResponseEntity<ApiResponse<List<RoleResponse>>> getAllRoles() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<RoleResponse>>builder()
                        .data(roleService.getAllRoles())
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteSoftRole(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateRole(@PathVariable Long id) {
        roleService.deactivateRole(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponse>> updateRole(
            @PathVariable Long id, @RequestBody @Valid RoleRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<RoleResponse>builder()
                        .data(roleService.updateRole(id, request))
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponse>> getRoleById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<RoleResponse>builder()
                        .data(roleService.getRoleById(id))
                        .build());
    }


    @PutMapping("/{roleId}/permissions")
    public ResponseEntity<ApiResponse<RoleResponse>> addPermissionsToRole(
            @PathVariable Long roleId, @RequestBody PermissionIdsRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<RoleResponse>builder()
                        .data(roleService.addRolePermissions(roleId, request))
                        .build());
    }

    @DeleteMapping("/{roleId}/permissions")
    public ResponseEntity<ApiResponse<RoleResponse>> removePermissionsFromRole(
            @PathVariable Long roleId, @RequestBody PermissionIdsRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<RoleResponse>builder()
                        .data(roleService.removePermissionFromRole(roleId, request))
                        .build());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<RoleResponse>> changeDestinationStatus(
            @PathVariable Long id, @RequestBody StatusRequest statusRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<RoleResponse>builder()
                        .data(roleService.changeRoleStatus(id, statusRequest))
                        .build());
    }
}
