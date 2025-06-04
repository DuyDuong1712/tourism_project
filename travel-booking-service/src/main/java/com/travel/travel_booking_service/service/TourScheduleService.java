package com.travel.travel_booking_service.service;

import com.travel.travel_booking_service.dto.request.TourRequest;
import com.travel.travel_booking_service.dto.response.TourResponse;

import java.util.List;

public interface TourScheduleService {
    TourResponse createTourSchedule(TourRequest request);

    List<TourResponse> getAllTourSchedules();

    TourResponse updateTourSchedules(Long id, TourRequest request);

    void deleteTour(Long id);

    List<TourResponse> searchTours(String keyword);

    List<TourResponse> getToursByCategory(Long categoryId);

    List<TourResponse> getToursByDestination(Long destinationId);
}
