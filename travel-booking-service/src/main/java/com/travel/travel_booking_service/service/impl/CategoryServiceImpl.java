package com.travel.travel_booking_service.service.impl;

import com.travel.travel_booking_service.dto.CategoryDTO;
import com.travel.travel_booking_service.entity.Category;
import com.travel.travel_booking_service.exception.BusinessException;
import com.travel.travel_booking_service.exception.DuplicateResourceException;
import com.travel.travel_booking_service.exception.ResourceNotFoundException;
import com.travel.travel_booking_service.mapper.CategoryMapper;
import com.travel.travel_booking_service.repository.CategoryRepository;
import com.travel.travel_booking_service.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        validateCategory(categoryDTO);

        if (isCategoryExists(categoryDTO.getName())) {
            throw new DuplicateResourceException("Category", "name", categoryDTO.getName());
        }

        Category category = categoryMapper.toEntity(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDTO(savedCategory);
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        validateCategory(categoryDTO);

        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        if (!existingCategory.getName().equals(categoryDTO.getName()) 
                && isCategoryExists(categoryDTO.getName())) {
            throw new DuplicateResourceException("Category", "name", categoryDTO.getName());
        }

        categoryMapper.updateEntityFromDTO(categoryDTO, existingCategory);
        Category updatedCategory = categoryRepository.save(existingCategory);
        return categoryMapper.toDTO(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        if (category.getTours() != null && !category.getTours().isEmpty()) {
            throw new BusinessException(ErrorCode.CATEGORY_IN_USE);
        }

        categoryRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        return categoryMapper.toDTO(category);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryDTO> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(categoryMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> getPopularCategories() {
        return categoryRepository.findPopularCategories().stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isCategoryExists(String name) {
        return categoryRepository.existsByName(name);
    }

    @Override
    public void validateCategory(CategoryDTO categoryDTO) {
        if (categoryDTO.getName() == null || categoryDTO.getName().trim().isEmpty()) {
            throw new BusinessException(ErrorCode.VALIDATION_ERROR, "Category name is required");
        }

        if (categoryDTO.getDescription() == null || categoryDTO.getDescription().trim().isEmpty()) {
            throw new BusinessException(ErrorCode.VALIDATION_ERROR, "Category description is required");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> getCategoriesWithTourCount() {
        return categoryRepository.findAllWithTourCount().stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
    }
} 