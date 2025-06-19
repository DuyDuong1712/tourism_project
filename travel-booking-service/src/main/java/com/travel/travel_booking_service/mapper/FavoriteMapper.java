package com.travel.travel_booking_service.mapper;

import com.travel.travel_booking_service.dto.request.DestinationRequest;
import com.travel.travel_booking_service.dto.request.FavoriteRequest;
import com.travel.travel_booking_service.dto.response.FavoriteResponse;
import com.travel.travel_booking_service.entity.Destination;
import com.travel.travel_booking_service.entity.Favorite;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FavoriteMapper {
    Favorite toFavoriteEntity(FavoriteRequest request);

    FavoriteResponse toFavoriteResponse(Favorite favorite);
}
