package com.travel.travel_booking_service.service;

import java.util.List;

import com.travel.travel_booking_service.dto.request.DepartureRequest;
import com.travel.travel_booking_service.dto.request.StatusRequest;
import com.travel.travel_booking_service.dto.response.DepartureResponse;
import com.travel.travel_booking_service.dto.response.StatisticResponse;

public interface DepartureService {
    DepartureResponse createDeparture(DepartureRequest departureRequest);

    DepartureResponse updateDeparture(Long id, DepartureRequest departureRequest);

    void deleteDeparture(Long id);

    List<DepartureResponse> getAllDeparture();

    List<DepartureResponse> getAllActiveDeparture();

    DepartureResponse changeDepartureStatus(Long id, StatusRequest statusRequest);

    StatisticResponse getDepartureStatistics();
}
