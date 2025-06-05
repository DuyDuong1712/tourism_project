package com.travel.travel_booking_service.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.travel.travel_booking_service.dto.request.FeaturedRequest;
import com.travel.travel_booking_service.dto.request.StatusRequest;
import com.travel.travel_booking_service.dto.request.ToursDetailsStatusRequest;
import com.travel.travel_booking_service.dto.response.TourDetailResponse;
import com.travel.travel_booking_service.dto.response.TourEditResponse;
import com.travel.travel_booking_service.dto.response.TourResponse;

public interface TourService {
    TourResponse createTour(
            String title,
            Boolean isFeatured,
            Long categoryId,
            Long destinationId,
            Long departureId,
            Long transportationId,
            String description,
            String informationJson,
            String scheduleJson,
            String tourDetailJson,
            List<MultipartFile> imageFiles)
            throws JsonProcessingException;

    List<TourResponse> getAllTours();

    List<TourResponse> getAllToursFiltered(
            Long destinationId,
            Long departureId,
            Long transportationId,
            Long categoryId,
            Boolean inActive,
            Boolean isFeatured,
            String title);

    // Lấy tất cả tour  + detail của tour đó
    List<TourDetailResponse> getAllToursWithDetails();

    List<TourDetailResponse> getTourDetailsByTourId(Long id);

    TourEditResponse getTourById(Long id);

    TourResponse updateTour(
            Long id,
            String title,
            Boolean isFeatured,
            Long categoryId,
            Long destinationId,
            Long departureId,
            Long transportationId,
            String description,
            String informationJson,
            String scheduleJson,
            String tourDetailJson,
            List<MultipartFile> imageFiles)
            throws JsonProcessingException;

    void deleteTour(Long id);

    List<TourResponse> searchTours(String keyword);

    List<TourResponse> getToursByCategory(Long categoryId);

    List<TourResponse> getToursByDestination(Long destinationId);

    void changeTourStatus(Long id, StatusRequest statusRequest);

    void changeTourFeatured(Long id, FeaturedRequest featuredRequest);

    void changeToursDetailsStatus(Long TourDetailId, ToursDetailsStatusRequest request);
}
