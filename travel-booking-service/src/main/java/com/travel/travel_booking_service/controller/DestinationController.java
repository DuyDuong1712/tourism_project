package com.travel.travel_booking_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travel.travel_booking_service.dto.response.ApiResponse;
import com.travel.travel_booking_service.dto.response.DestinationResponse;
import com.travel.travel_booking_service.service.DestinationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/destinations")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
// @PreAuthorize("hasRole('ADMIN')")
public class DestinationController {

    DestinationService destinationService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<DestinationResponse>>> getAllDestinations() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<DestinationResponse>>builder()
                        .data(destinationService.getAllActiveDestinations())
                        .build());
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<DestinationResponse>>> searchDestinations(
            @RequestParam(required = false) String keyword) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<DestinationResponse>>builder()
                        .data(destinationService.searchDestinations(keyword))
                        .build());
    }

    @GetMapping("/parent-list")
    public ResponseEntity<ApiResponse<List<DestinationResponse>>> getParentDestinations() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<DestinationResponse>>builder()
                        .data(destinationService.getParentDestinations())
                        .build());
    }

    @GetMapping("/{id}/children")
    public ResponseEntity<ApiResponse<List<DestinationResponse>>> getChildrenDestinations(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<DestinationResponse>>builder()
                        .data(destinationService.getChildrenByParentId(id))
                        .build());
    }
}
