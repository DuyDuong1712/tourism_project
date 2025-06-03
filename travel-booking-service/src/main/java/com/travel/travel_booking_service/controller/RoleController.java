package com.travel.travel_booking_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travel.travel_booking_service.dto.request.RoleRequest;
import com.travel.travel_booking_service.dto.response.*;
import com.travel.travel_booking_service.service.PermissionService;
import com.travel.travel_booking_service.service.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {

    RoleService roleService;
    PermissionService permissionService;

    @PostMapping
    public ResponseEntity<ApiResponse<RoleResponse>> createRole(@RequestBody RoleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<RoleResponse>builder()
                        .data(roleService.createRole(request))
                        .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponse>> updateRole(
            @PathVariable Long id, @RequestBody RoleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<RoleResponse>builder()
                        .data(roleService.updateRole(id, request))
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteSoftRole(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponse>> getRoleById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<RoleResponse>builder()
                        .data(roleService.getRoleById(id))
                        .build());
    }

    @GetMapping("/code")
    public ResponseEntity<ApiResponse<RoleResponse>> getRoleByCode(@RequestParam String code) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<RoleResponse>builder()
                        .data(roleService.getRoleByCode(code))
                        .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RoleResponse>>> getAllRoles() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<RoleResponse>>builder()
                        .data(roleService.getAllRoles())
                        .build());
    }

    //    @PostMapping("/{roleId}/permissions/{permissionId}")
    //    public ResponseEntity<ApiResponse<Void>> addPermissionToRole(
    //            @PathVariable Long roleId,
    //            @PathVariable Long permissionId) {
    //        roleService.addPermissionToRole(roleId, permissionId);
    //        return ResponseEntity.status(HttpStatus.CREATED).build();
    //    }

    //    @DeleteMapping("/{roleId}/permissions/{permissionId}")
    //    public ResponseEntity<ApiResponse<Void>> removePermissionFromRole(
    //            @PathVariable Long roleId, @PathVariable Long permissionId) {
    //        roleService.removePermissionFromRole(roleId, permissionId);
    //        return ResponseEntity.noContent().build();
}

// Xem chi tiết role đã trả ve list per
	// Xem các permission của một role
	//    @GetMapping("/{roleId}/permissions")
	//    public ResponseEntity<ApiResponse<RoleResponse>> getPermissionsByRoleId(@PathVariable Long roleId) {
	//        return ResponseEntity.status(HttpStatus.OK).body(
	//                ApiResponse.<RoleResponse>builder()
	//                        .data(roleService.getPermissionsByRoleId(roleId))
	//                        .build()
	//        );
	//    }

// Gán permission cho role //CHưa hoàn thiện
	//
	//    @PostMapping("/{roleId}/permissions")
	//    public ResponseEntity<ApiResponse<RoleResponse>> updateRolePermissions(@PathVariable Long roleId,
	//                                                                                       @RequestBody
	// RolePermissionUpdateRequest request) {
	//        return ResponseEntity.status(HttpStatus.OK).body(
	//                ApiResponse.<RoleResponse>builder()
	//                        .data(roleService.updateRolePermissions(roleId, request.getPermissionIds()))
	//                        .build()
	//        );
	//    }
