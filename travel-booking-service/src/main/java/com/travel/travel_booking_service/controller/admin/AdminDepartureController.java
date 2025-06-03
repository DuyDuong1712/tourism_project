package com.travel.travel_booking_service.controller.admin;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travel.travel_booking_service.dto.request.DepartureRequest;
import com.travel.travel_booking_service.dto.request.StatusRequest;
import com.travel.travel_booking_service.dto.response.ApiResponse;
import com.travel.travel_booking_service.dto.response.DepartureResponse;
import com.travel.travel_booking_service.service.DepartureService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/admin/departures")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
// @PreAuthorize("hasRole('ADMIN')")
public class AdminDepartureController {

    DepartureService departureService;

    @PostMapping
    public ResponseEntity<ApiResponse<DepartureResponse>> createDeparture(
            @RequestBody @Valid DepartureRequest departureRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<DepartureResponse>builder()
                        .data(departureService.createDeparture(departureRequest))
                        .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartureResponse>>> getAllDeparture() {
        List<DepartureResponse> departures = departureService.getAllDeparture();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<DepartureResponse>>builder()
                        .data(departures)
                        .build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartureResponse>> updateDeparture(
            @PathVariable Long id, @RequestBody @Valid DepartureRequest departureRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<DepartureResponse>builder()
                        .data(departureService.updateDeparture(id, departureRequest))
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeparture(@PathVariable Long id) {
        departureService.deleteDeparture(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<DepartureResponse>> changeDepartureStatus(
            @PathVariable Long id, @RequestBody StatusRequest statusRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<DepartureResponse>builder()
                        .data(departureService.changeDepartureStatus(id, statusRequest))
                        .build());
    }
}
