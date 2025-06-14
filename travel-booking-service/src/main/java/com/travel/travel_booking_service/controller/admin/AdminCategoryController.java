package com.travel.travel_booking_service.controller.admin;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travel.travel_booking_service.dto.request.CategoryRequest;
import com.travel.travel_booking_service.dto.request.StatusRequest;
import com.travel.travel_booking_service.dto.response.ApiResponse;
import com.travel.travel_booking_service.dto.response.CategoryResponse;
import com.travel.travel_booking_service.dto.response.StatisticResponse;
import com.travel.travel_booking_service.service.CategoryService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminCategoryController {

    CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(@RequestBody @Valid CategoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<CategoryResponse>builder()
                        .data(categoryService.createCategory(request))
                        .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategories() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<CategoryResponse>>builder()
                        .data(categoryService.getAllCategories())
                        .build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(
            @PathVariable Long id, @RequestBody @Valid CategoryRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<CategoryResponse>builder()
                        .data(categoryService.updateCategory(id, request))
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<CategoryResponse>> changeCategoryStatus(
            @PathVariable Long id, @RequestBody StatusRequest statusRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<CategoryResponse>builder()
                        .data(categoryService.changeCategoryStatus(id, statusRequest))
                        .build());
    }

    @GetMapping("/statistics")
    public ResponseEntity<ApiResponse<StatisticResponse>> getCategoryStatistics() {
        StatisticResponse statistics = categoryService.getCategoryStatistics();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<StatisticResponse>builder().data(statistics).build());
    }
}
