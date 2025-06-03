package com.travel.travel_booking_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.travel.travel_booking_service.dto.request.DestinationRequest;
import com.travel.travel_booking_service.dto.response.DestinationResponse;
import com.travel.travel_booking_service.entity.Destination;

@Mapper(componentModel = "spring")
public interface DestinationMapper {
    Destination toDestinationEntity(DestinationRequest request);

    void updateDestination(@MappingTarget Destination destination, DestinationRequest request);

    DestinationResponse toDestinationResponse(Destination destination);
}
