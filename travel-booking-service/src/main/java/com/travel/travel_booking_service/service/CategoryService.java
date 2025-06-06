package com.travel.travel_booking_service.service;

import java.util.List;

import com.travel.travel_booking_service.dto.request.CategoryRequest;
import com.travel.travel_booking_service.dto.request.StatusRequest;
import com.travel.travel_booking_service.dto.response.CategoryResponse;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);

    List<CategoryResponse> getAllCategories();

    List<CategoryResponse> getAllActiveCategories();

    CategoryResponse updateCategory(Long id, CategoryRequest request);

    void deleteCategory(Long id);

    CategoryResponse changeCategoryStatus(Long id, StatusRequest statusRequest);
}
