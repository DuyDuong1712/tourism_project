package com.travel.travel_booking_service.controller.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.travel.travel_booking_service.dto.request.StatusRequest;
import com.travel.travel_booking_service.dto.response.ApiResponse;
import com.travel.travel_booking_service.dto.response.DestinationResponse;
import com.travel.travel_booking_service.service.DestinationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/admin/destinations")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
// @PreAuthorize("hasRole('ADMIN')")
public class AdminDestinationController {

    DestinationService destinationService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<DestinationResponse>> createDestination(
            @RequestParam("name") String name,
            @RequestParam("code") String code,
            @RequestParam("description") String description,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<DestinationResponse>builder()
                        .data(destinationService.createDestination(name, code, description, imageFile))
                        .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DestinationResponse>>> getAllDestinations() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<DestinationResponse>>builder()
                        .data(destinationService.getAllDestinations())
                        .build());
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<DestinationResponse>> updateDestination(
            @PathVariable("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("code") String code,
            @RequestParam("description") String description,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<DestinationResponse>builder()
                        .data(destinationService.updateDestination(id, name, code, description, imageFile))
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDestination(@PathVariable Long id) {
        destinationService.deleteDestination(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<DestinationResponse>> changeDestinationStatus(
            @PathVariable Long id, @RequestBody StatusRequest statusRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<DestinationResponse>builder()
                        .data(destinationService.changeDestinationStatus(id, statusRequest))
                        .build());
    }
}
