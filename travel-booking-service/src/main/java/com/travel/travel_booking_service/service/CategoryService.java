package com.travel.travel_booking_service.service;

import com.travel.travel_booking_service.dto.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);
    void deleteCategory(Long id);
    CategoryDTO getCategoryById(Long id);
    Page<CategoryDTO> getAllCategories(Pageable pageable);
    List<CategoryDTO> getPopularCategories();
    boolean isCategoryExists(String name);
    void validateCategory(CategoryDTO categoryDTO);
    List<CategoryDTO> getCategoriesWithTourCount();
} 