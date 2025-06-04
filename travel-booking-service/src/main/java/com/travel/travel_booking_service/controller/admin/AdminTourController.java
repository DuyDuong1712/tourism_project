package com.travel.travel_booking_service.controller.admin;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.travel.travel_booking_service.dto.request.TourDetailRequest;
import com.travel.travel_booking_service.dto.request.TourInfomationRequest;
import com.travel.travel_booking_service.dto.request.TourScheduleRequest;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travel.travel_booking_service.dto.request.TourRequest;
import com.travel.travel_booking_service.dto.response.ApiResponse;
import com.travel.travel_booking_service.dto.response.TourResponse;
import com.travel.travel_booking_service.service.TourService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin/tours")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminTourController {

    TourService tourService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<TourResponse>> createTour(
            @RequestParam("title") String title,
            @RequestParam(value = "isFeatured", required = false) Boolean isFeatured,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("destinationId") Long destinationId,
            @RequestParam("departureId") Long departureId,
            @RequestParam("transportationId") Long transportationId,
            @RequestParam("description") String description,
            @RequestParam("information") String informationJson,
            @RequestParam("schedule") String scheduleJson,
            @RequestParam("tour_detail") String tourDetailJson,
            @RequestParam(value = "images", required = false) List<MultipartFile> imageFiles
    ) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<TourResponse>builder()
                        .data(tourService.createTour(title,
                                isFeatured,
                                categoryId,
                                destinationId,
                                departureId,
                                transportationId,
                                description,
                                informationJson,
                                scheduleJson,
                                tourDetailJson,
                                imageFiles
                        ))
                        .build());
    }

    @GetMapping("tours-with-details")
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
