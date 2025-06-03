package com.travel.travel_booking_service.controller;

import org.springframework.web.bind.annotation.*;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/transports")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransportController {
    //    TransportService transportService;
    //
    //    @PostMapping()
    //    public ResponseEntity<ApiResponse<TransportResponse>> createCategory(@RequestBody @Valid TransportRequest
    // request) {
    //        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<TransportResponse>builder()
    //                                                                .data(transportService.addTransport(request))
    //                                                                .build());
    //    }
    //
    //    @GetMapping("")
    //    public ResponseEntity<ApiResponse<List<TransportResponse>>> getAllCategories() {
    //        return ResponseEntity.status(HttpStatus.OK).body(
    //                ApiResponse.<List<TransportResponse>>builder()
    //                        .data(transportService.getAllTransports())
    //                        .build()
    //        );
    //    }
    //
    //    @GetMapping("/{id}")
    //    public ResponseEntity<ApiResponse<TransportResponse>> getCategoryById(@PathVariable Long id) {
    //        return ResponseEntity.status(HttpStatus.OK).body(
    //                ApiResponse.<TransportResponse>builder()
    //                .data(transportService.getTransportById(id))
    //                .build()
    //        );
    //    }
    //
    //    @GetMapping("/search")
    //    public ResponseEntity<ApiResponse<List<TransportResponse>>> getCategoryByName(@RequestParam String keyword) {
    //        return ResponseEntity.status(HttpStatus.OK).body(
    //                ApiResponse.<List<TransportResponse>>builder()
    //                        .data(transportService.searchTransport(keyword))
    //                        .build()
    //        );
    //    }
    //
    //    @PutMapping("/{id}")
    //    public ResponseEntity<ApiResponse<TransportResponse>> updateCategory(@PathVariable Long id, @RequestBody
    // TransportRequest request) {
    //        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<TransportResponse>builder()
    //                .data(transportService.updateTransport(id, request))
    //                .build());
    //    }
    //
    //    @DeleteMapping("/{id}")
    //    public ResponseEntity deleteCategory(@PathVariable Long id) {
    //        transportService.deleteSoftTransport(id);
    //        return ResponseEntity.noContent().build();
    //    }
}
