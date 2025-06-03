package com.travel.travel_booking_service.controller.admin;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travel.travel_booking_service.dto.request.StatusRequest;
import com.travel.travel_booking_service.dto.request.TransportRequest;
import com.travel.travel_booking_service.dto.response.ApiResponse;
import com.travel.travel_booking_service.dto.response.TransportResponse;
import com.travel.travel_booking_service.service.TransportService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/admin/transportations")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminTransportController {

    TransportService transportService;

    @PostMapping
    public ResponseEntity<ApiResponse<TransportResponse>> createTransport(
            @RequestBody @Valid TransportRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<TransportResponse>builder()
                        .data(transportService.createTransport(request))
                        .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TransportResponse>>> getAllTransports() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<TransportResponse>>builder()
                        .data(transportService.getAllTransports())
                        .build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<TransportResponse>> updateTransport(
            @PathVariable Long id, @RequestBody @Valid TransportRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<TransportResponse>builder()
                        .data(transportService.updateTransport(id, request))
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransport(@PathVariable Long id) {
        transportService.deleteTransport(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<TransportResponse>> changeTransportStatus(
            @PathVariable Long id, @RequestBody StatusRequest statusRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<TransportResponse>builder()
                        .data(transportService.changeTransportStatus(id, statusRequest))
                        .build());
    }
}
