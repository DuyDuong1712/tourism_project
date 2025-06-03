package com.travel.travel_booking_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.travel.travel_booking_service.dto.request.DepartureRequest;
import com.travel.travel_booking_service.dto.response.DepartureResponse;
import com.travel.travel_booking_service.entity.Departure;

@Mapper(componentModel = "spring")
public interface DepartureMapper {
    Departure toDepartureEntity(DepartureRequest request);

    void updateDeparture(@MappingTarget Departure departure, DepartureRequest request);

    DepartureResponse toDepartureResponse(Departure departure);
}
