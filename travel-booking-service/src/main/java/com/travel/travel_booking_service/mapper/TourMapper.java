package com.travel.travel_booking_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.travel.travel_booking_service.dto.request.CategoryRequest;
import com.travel.travel_booking_service.entity.Category;

@Mapper(componentModel = "spring")
public interface TourMapper {
    Category toCategoryEntity(CategoryRequest request);

    void updateCategory(@MappingTarget Category category, CategoryRequest request);

    //    TourResponse toTourResponse(Tour tour);
}
