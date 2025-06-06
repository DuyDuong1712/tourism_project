package com.travel.travel_booking_service.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travel.travel_booking_service.dto.response.*;
import com.travel.travel_booking_service.service.TourService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/tours")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TourController {

    TourService tourService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<CustomerTourSearchResponse>>> getAllActiveToursWithFilter(
            @RequestParam(required = false) Integer departureId,
            @RequestParam(required = false) Integer budgetId,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer transTypeId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<CustomerTourSearchResponse>>builder()
                        .data(tourService.getAllActiveToursWithFilter(
                                departureId, budgetId, categoryId, transTypeId, fromDate))
                        .build());
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ApiResponse<List<CustomerTourSearchResponse>>> getAllActiveToursWithFilterWithSlug(
            @PathVariable String slug,
            @RequestParam(required = false) Integer departureId,
            @RequestParam(required = false) Integer budgetId,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer transTypeId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<CustomerTourSearchResponse>>builder()
                        .data(tourService.getAllActiveToursWithFilterWithSlug(
                                slug, departureId, budgetId, categoryId, transTypeId, fromDate))
                        .build());
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<ApiResponse<CustomerTourViewResponse>> getTourDetails(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<CustomerTourViewResponse>builder()
                        .data(tourService.getTourDetails(id))
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

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<TourResponse>>> searchTours(@RequestParam String keyword) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<TourResponse>>builder()
                        .data(tourService.searchTours(keyword))
                        .build());
    }
}
