package com.travel.travel_booking_service.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.travel.travel_booking_service.dto.request.TourDetailRequest;
import com.travel.travel_booking_service.dto.request.TourInfomationRequest;
import com.travel.travel_booking_service.dto.request.TourRequest;
import com.travel.travel_booking_service.dto.request.TourScheduleRequest;
import com.travel.travel_booking_service.dto.response.TourDetailResponse;
import com.travel.travel_booking_service.dto.response.TourResponse;
import org.springframework.web.multipart.MultipartFile;

public interface TourService {
    TourResponse createTour(String title,
                                    Boolean isFeatured,
                                    Long categoryId,
                                    Long destinationId,
                                    Long departureId,
                                    Long transportationId,
                                    String description,
                                    String informationJsoninformationJson,
                                    String scheduleJson,
                                    String tourDetailJsontourDetailJson,
                                    List<MultipartFile> imageFiles) throws JsonProcessingException;

    List<TourResponse> getAllTours();

    //Lấy tất cả tour  + detail của tour đó
    List<TourDetailResponse> getAllToursWithDetails();

    TourResponse getTourById(Long id);

    TourResponse updateTour(Long id, TourRequest request);

    void deleteTour(Long id);

    List<TourResponse> searchTours(String keyword);

    List<TourResponse> getToursByCategory(Long categoryId);

    List<TourResponse> getToursByDestination(Long destinationId);
}
