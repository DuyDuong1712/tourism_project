package com.travel.travel_booking_service.service;

import java.util.List;

import com.travel.travel_booking_service.dto.request.StatusRequest;
import com.travel.travel_booking_service.dto.request.TransportRequest;
import com.travel.travel_booking_service.dto.response.StatisticResponse;
import com.travel.travel_booking_service.dto.response.TransportResponse;

public interface TransportService {
    TransportResponse createTransport(TransportRequest request);

    List<TransportResponse> getAllTransports();

    List<TransportResponse> getAllActiveTransports();

    TransportResponse updateTransport(Long id, TransportRequest request);

    void deleteTransport(Long id);

    TransportResponse changeTransportStatus(Long id, StatusRequest statusRequest);

    StatisticResponse getTransportStatistics();
}
