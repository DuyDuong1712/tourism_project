package com.travel.travel_booking_service.service;

import com.travel.travel_booking_service.dto.request.FavoriteRequest;
import com.travel.travel_booking_service.dto.response.CustomerFavoriteViewResponse;
import com.travel.travel_booking_service.dto.response.FavoriteResponse;
import com.travel.travel_booking_service.entity.Favorite;
import com.travel.travel_booking_service.repository.FavoriteRepository;

import java.util.List;

public interface FavoriteService {
    FavoriteResponse addToFavorites(FavoriteRequest request);

    void removeFromFavorites(FavoriteRequest request);

    List<FavoriteResponse> getFavorites();

    boolean isTourFavoritedByUser(Long userId, Long tourId);

    List<CustomerFavoriteViewResponse> getFavoritesByUserId(Long userId);
}
