package com.travel.travel_booking_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.travel.travel_booking_service.dto.request.CategoryRequest;
import com.travel.travel_booking_service.dto.response.TourResponse;
import com.travel.travel_booking_service.entity.Category;
import com.travel.travel_booking_service.entity.Tour;

@Mapper(componentModel = "spring")
public interface TourMapper {
    @Mapping(target = "tourImages", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "departure", ignore = true)
    @Mapping(target = "destination", ignore = true)
    @Mapping(target = "transportation", ignore = true)
    TourResponse toTourResponse(Tour tour);

    void updateCategory(@MappingTarget Category category, CategoryRequest request);

    //    TourResponse toTourResponse(Tour tour);
}
