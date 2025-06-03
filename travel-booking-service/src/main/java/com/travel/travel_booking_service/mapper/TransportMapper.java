package com.travel.travel_booking_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.travel.travel_booking_service.dto.request.TransportRequest;
import com.travel.travel_booking_service.dto.response.TransportResponse;
import com.travel.travel_booking_service.entity.Transport;

@Mapper(componentModel = "spring")
public interface TransportMapper {
    Transport toTransportEntity(TransportRequest request);

    void updateTransport(@MappingTarget Transport transport, TransportRequest request);

    TransportResponse toTransportResponse(Transport transport);
}
