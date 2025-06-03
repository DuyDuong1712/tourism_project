package com.travel.travel_booking_service.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travel.travel_booking_service.dto.request.PermissionRequest;
import com.travel.travel_booking_service.dto.response.ApiResponse;
import com.travel.travel_booking_service.dto.response.PermissionResponse;
import com.travel.travel_booking_service.service.PermissionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {

    PermissionService permissionService;

    @PostMapping()
    public ResponseEntity<ApiResponse<PermissionResponse>> addPermission(
            @RequestBody @Valid PermissionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<PermissionResponse>builder()
                        .data(permissionService.addPermission(request))
                        .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PermissionResponse>> updatePermission(
            @PathVariable Long id, @RequestBody @Valid PermissionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<PermissionResponse>builder()
                        .data(permissionService.updatePermission(id, request))
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        permissionService.deleteSoftPermission(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<PermissionResponse>>> getAllActivePermissions() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<PermissionResponse>>builder()
                        .data(permissionService.getAllPermissions())
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PermissionResponse>> getPermissionById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<PermissionResponse>builder()
                        .data(permissionService.getPermissionById(id))
                        .build());
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<ApiResponse<PermissionResponse>> getPermissionByCode(@PathVariable String code) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<PermissionResponse>builder()
                        .data(permissionService.getPermissionByCode(code))
                        .build());
    }

    //    @GetMapping
    //    public ResponseEntity<Page<Permission>> getAllPermissions(Pageable pageable) {
    //        return ResponseEntity.ok(permissionService.getAllPermissions(pageable));
    //    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<PermissionResponse>>> searchPermissionsByName(@RequestParam String name) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<PermissionResponse>>builder()
                        .data(permissionService.searchPermissionsByName(name))
                        .build());
    }
}
