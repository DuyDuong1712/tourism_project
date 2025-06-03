package com.travel.travel_booking_service.controller.admin;

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
@RequestMapping("/api/admin/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
// @PreAuthorize("hasRole('ADMIN')")
public class AdminPermissionController {

    PermissionService permissionService;

    @PostMapping
    public ResponseEntity<ApiResponse<PermissionResponse>> createPermission(
            @RequestBody @Valid PermissionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<PermissionResponse>builder()
                        .data(permissionService.addPermission(request))
                        .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PermissionResponse>>> getAllPermissions() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<PermissionResponse>>builder()
                        .data(permissionService.getAllPermissions())
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDestination(@PathVariable Long id) {
        permissionService.deleteSoftPermission(id);
        return ResponseEntity.noContent().build();
    }

    //
    //    @GetMapping("/{id}")
    //    public ResponseEntity<ApiResponse<DestinationResponse>> getDestinationById(@PathVariable Long id) {
    //        return ResponseEntity.status(HttpStatus.OK).body(
    //                ApiResponse.<DestinationResponse>builder()
    //                        .data(destinationService.getDestinationById(id))
    //                        .build()
    //        );
    //    }
    //
    //    @PutMapping("/{id}")
    //    public ResponseEntity<ApiResponse<DestinationResponse>> updateDestination(@PathVariable Long id,
    //            @RequestBody @Valid DestinationRequest request) {
    //        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<DestinationResponse>builder()
    //                .data(destinationService.updateDestination(id, request))
    //                .build());
    //    }
    //

    //
    //    @GetMapping("/search")
    //    public ResponseEntity<ApiResponse<List<DestinationResponse>>> searchDestinations(@RequestParam String keyword)
    // {
    //        return ResponseEntity.status(HttpStatus.OK).body(
    //                ApiResponse.<List<DestinationResponse>>builder()
    //                        .data(destinationService.searchDestinations(keyword))
    //                        .build()
    //        );
    //    }
    //
    //    @PutMapping("{id}/change-status")
    //    public ResponseEntity<ApiResponse<DestinationResponse>> changeDestinationStatus(@PathVariable Long id,
    // @RequestBody Integer statusRequest) {
    //        return ResponseEntity.status(HttpStatus.OK).body(
    //                ApiResponse.<DestinationResponse>builder()
    //                        .data(destinationService.changeActiveDestination(id, statusRequest))
    //                        .build()
    //        );
    //    }
}
