package com.travel.travel_booking_service.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.travel.travel_booking_service.dto.request.StatusRequest;
import com.travel.travel_booking_service.dto.response.DestinationResponse;
import com.travel.travel_booking_service.dto.response.StatisticResponse;

public interface DestinationService {
    // ADMIN
    DestinationResponse createDestination(
            String name, String code, String description, Long parentId, MultipartFile imageFile);

    DestinationResponse updateDestination(
            Long id, String name, String code, String description, Long parentId, MultipartFile imageFile);

    void deleteDestination(Long id);

    List<DestinationResponse> getAllDestinations();

    List<DestinationResponse> getAllActiveDestinations();

    DestinationResponse changeDestinationStatus(Long id, StatusRequest statusRequest);

    DestinationResponse getDestinationById(Long id);

    List<DestinationResponse> getParentDestinations();

    List<DestinationResponse> getChildrenByParentId(Long id);

    StatisticResponse getDestinationStatistics();
}
