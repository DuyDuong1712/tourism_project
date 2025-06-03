package com.travel.travel_booking_service.service;

import java.util.List;

import com.travel.travel_booking_service.dto.request.TourRequest;
import com.travel.travel_booking_service.dto.response.TourResponse;

public interface TourService {
    TourResponse createTour(TourRequest request);

    List<TourResponse> getAllTours();

    TourResponse getTourById(Long id);

    TourResponse updateTour(Long id, TourRequest request);

    void deleteTour(Long id);

    List<TourResponse> searchTours(String keyword);

    List<TourResponse> getToursByCategory(Long categoryId);

    List<TourResponse> getToursByDestination(Long destinationId);
}
