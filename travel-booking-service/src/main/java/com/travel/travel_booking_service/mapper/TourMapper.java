package com.travel.travel_booking_service.mapper;

import com.travel.travel_booking_service.dto.request.CategoryRequest;
import com.travel.travel_booking_service.dto.response.CategoryResponse;
import com.travel.travel_booking_service.dto.response.TourResponse;
import com.travel.travel_booking_service.entity.Category;
import com.travel.travel_booking_service.entity.Tour;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TourMapper {
    Category toCategoryEntity(CategoryRequest request);

    void updateCategory(@MappingTarget Category category, CategoryRequest request);

    TourResponse toTourResponse(Tour tour);
}
