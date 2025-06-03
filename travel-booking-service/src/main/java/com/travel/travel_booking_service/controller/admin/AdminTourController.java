package com.travel.travel_booking_service.controller.admin;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travel.travel_booking_service.dto.request.TourRequest;
import com.travel.travel_booking_service.dto.response.ApiResponse;
import com.travel.travel_booking_service.dto.response.TourResponse;
import com.travel.travel_booking_service.service.TourService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/admin/tours")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminTourController {

    TourService tourService;

    @PostMapping
    public ResponseEntity<ApiResponse<TourResponse>> createTour(@RequestBody @Valid TourRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<TourResponse>builder()
                        .data(tourService.createTour(request))
                        .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TourResponse>>> getAllTours() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<TourResponse>>builder()
                        .data(tourService.getAllTours())
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TourResponse>> getTourById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<TourResponse>builder()
                        .data(tourService.getTourById(id))
                        .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TourResponse>> updateTour(
            @PathVariable Long id, @RequestBody @Valid TourRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<TourResponse>builder()
                        .data(tourService.updateTour(id, request))
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable Long id) {
        tourService.deleteTour(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<TourResponse>>> searchTours(@RequestParam String keyword) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<TourResponse>>builder()
                        .data(tourService.searchTours(keyword))
                        .build());
    }
}
