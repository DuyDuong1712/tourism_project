package com.travel.travel_booking_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travel.travel_booking_service.dto.response.ApiResponse;
import com.travel.travel_booking_service.dto.response.DepartureResponse;
import com.travel.travel_booking_service.service.DepartureService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/departures")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
// @PreAuthorize("hasRole('ADMIN')")
public class DepartureController {

    DepartureService departureService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartureResponse>>> getAllActiveDeparture() {
        List<DepartureResponse> departures = departureService.getAllActiveDeparture();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<DepartureResponse>>builder()
                        .data(departures)
                        .build());
    }
}
