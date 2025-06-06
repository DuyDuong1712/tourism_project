package com.travel.travel_booking_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travel.travel_booking_service.dto.response.ApiResponse;
import com.travel.travel_booking_service.dto.response.TransportResponse;
import com.travel.travel_booking_service.service.TransportService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/transportations")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransportController {

    TransportService transportService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<TransportResponse>>> getAllActiveTransports() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<TransportResponse>>builder()
                        .data(transportService.getAllActiveTransports())
                        .build());
    }
}
