package com.travel.travel_booking_service.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.travel_booking_service.dto.request.CategoryRequest;
import com.travel.travel_booking_service.dto.request.StatusRequest;
import com.travel.travel_booking_service.dto.response.CategoryResponse;
import com.travel.travel_booking_service.entity.Category;
import com.travel.travel_booking_service.enums.ErrorCode;
import com.travel.travel_booking_service.exception.AppException;
import com.travel.travel_booking_service.mapper.CategoryMapper;
import com.travel.travel_booking_service.repository.CategoryRepository;
import com.travel.travel_booking_service.service.CategoryService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        if (categoryRepository.existsByNameIgnoreCase(request.getName().trim())) {
            throw new AppException(ErrorCode.CATEGORY_EXISTS);
        }

        Category category = categoryMapper.toCategoryEntity(request);

        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(categoryMapper::toCategoryResponse).collect(Collectors.toList());
    }

    @Override
    public List<CategoryResponse> getAllActiveCategories() {
        List<Category> categories = categoryRepository.findByInActiveTrue();
        return categories.stream().map(categoryMapper::toCategoryResponse).collect(Collectors.toList());
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category =
                categoryRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        if (!category.getName().equalsIgnoreCase(request.getName())
                && categoryRepository.existsByNameIgnoreCase(request.getName())) {
            throw new AppException(ErrorCode.CATEGORY_EXISTS);
        }

        categoryMapper.updateCategory(category, request);

        categoryRepository.save(category);

        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category =
                categoryRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        if (category.getTours() != null && !category.getTours().isEmpty()) {
            throw new AppException(ErrorCode.CATEGORY_IN_USE);
        }

        categoryRepository.delete(category);
    }

    @Override
    public CategoryResponse changeCategoryStatus(Long id, StatusRequest statusRequest) {
        Category category =
                categoryRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        category.setInActive(statusRequest.getInActive());

        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }
}
