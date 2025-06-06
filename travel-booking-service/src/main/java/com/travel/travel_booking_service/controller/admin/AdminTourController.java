package com.travel.travel_booking_service.controller.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.travel.travel_booking_service.dto.request.FeaturedRequest;
import com.travel.travel_booking_service.dto.request.StatusRequest;
import com.travel.travel_booking_service.dto.request.ToursDetailsStatusRequest;
import com.travel.travel_booking_service.dto.response.*;
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
            @RequestParam(value = "images", required = false) List<MultipartFile> imageFiles)
            throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<TourResponse>builder()
                        .data(tourService.createTour(
                                title,
                                isFeatured,
                                categoryId,
                                destinationId,
                                departureId,
                                transportationId,
                                description,
                                informationJson,
                                scheduleJson,
                                tourDetailJson,
                                imageFiles))
                        .build());
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<TourResponse>>> getAllTours() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<TourResponse>>builder()
                        .data(tourService.getAllTours())
                        .build());
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<List<TourResponse>>> getAllToursFiltered(
            @RequestParam(required = false) Long destinationId,
            @RequestParam(required = false) Long departureId,
            @RequestParam(required = false) Long transportationId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Boolean inActive,
            @RequestParam(required = false) Boolean isFeatured,
            @RequestParam(required = false) String title) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<TourResponse>>builder()
                        .data(tourService.getAllToursFiltered(
                                destinationId, departureId, transportationId, categoryId, inActive, isFeatured, title))
                        .build());
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<ApiResponse<TourDetailViewResponse>> getTourDetails(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<TourDetailViewResponse>builder()
                        .data(tourService.getTourDetailsViewByTourId(id))
                        .build());
    }

    @GetMapping("/tours-with-details")
    public ResponseEntity<ApiResponse<List<TourDetailResponse>>> getAllToursWithDetail() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<TourDetailResponse>>builder()
                        .data(tourService.getAllToursWithDetails())
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TourEditResponse>> getTourById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<TourEditResponse>builder()
                        .data(tourService.getTourById(id))
                        .build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<TourResponse>> updateTour(
            @PathVariable Long id,
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
            @RequestParam(value = "images", required = false) List<MultipartFile> imageFiles)
            throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<TourResponse>builder()
                        .data(tourService.updateTour(
                                id,
                                title,
                                isFeatured,
                                categoryId,
                                destinationId,
                                departureId,
                                transportationId,
                                description,
                                informationJson,
                                scheduleJson,
                                tourDetailJson,
                                imageFiles))
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

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Void>> changeToursStatus(
            @PathVariable Long id, @RequestBody StatusRequest statusRequest) {
        tourService.changeTourStatus(id, statusRequest);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/featured")
    public ResponseEntity<ApiResponse<Void>> changeToursFeatured(
            @PathVariable Long id, @RequestBody FeaturedRequest featuredRequest) {
        tourService.changeTourFeatured(id, featuredRequest);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/details/{tourDetailsId}")
    public ResponseEntity<ApiResponse<Void>> changeToursDetailsStatus(
            @PathVariable Long tourDetailsId, @RequestBody ToursDetailsStatusRequest request) {
        tourService.changeToursDetailsStatus(tourDetailsId, request);
        return ResponseEntity.noContent().build();
    }
}
