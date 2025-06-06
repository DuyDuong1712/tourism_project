package com.travel.travel_booking_service.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.travel_booking_service.dto.request.DepartureRequest;
import com.travel.travel_booking_service.dto.request.StatusRequest;
import com.travel.travel_booking_service.dto.response.DepartureResponse;
import com.travel.travel_booking_service.entity.*;
import com.travel.travel_booking_service.enums.ErrorCode;
import com.travel.travel_booking_service.exception.AppException;
import com.travel.travel_booking_service.mapper.DepartureMapper;
import com.travel.travel_booking_service.repository.DepartureRepository;
import com.travel.travel_booking_service.service.DepartureService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartureServiceImpl implements DepartureService {

    DepartureRepository departureRepository;
    DepartureMapper departureMapper;

    @Override
    public DepartureResponse createDeparture(DepartureRequest departureRequest) {
        if (departureRepository.existsByNameIgnoreCase(departureRequest.getName())) {
            throw new AppException(ErrorCode.DEPARTURE_EXISTS);
        }

        if (departureRepository.existsByCodeIgnoreCase(departureRequest.getCode())) {
            throw new AppException(ErrorCode.DEPARTURE_CODE_EXISTS);
        }

        Departure departure = departureMapper.toDepartureEntity(departureRequest);

        return departureMapper.toDepartureResponse(departureRepository.save(departure));
    }

    @Override
    public DepartureResponse updateDeparture(Long id, DepartureRequest departureRequest) {
        Departure departure =
                departureRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.DEPARTURE_NOT_FOUND));

        boolean isNameChanged = !departure.getName().equalsIgnoreCase(departureRequest.getName());
        boolean isCodeChanged = !departure.getCode().equalsIgnoreCase(departureRequest.getCode());

        if ((isNameChanged && departureRepository.existsByNameIgnoreCase(departureRequest.getName()))
                || (isCodeChanged && departureRepository.existsByCodeIgnoreCase(departureRequest.getCode()))) {
            throw new AppException(ErrorCode.DEPARTURE_EXISTS);
        }

        departureMapper.updateDeparture(departure, departureRequest);

        departureRepository.save(departure);

        return departureMapper.toDepartureResponse(departure);
    }

    @Override
    public void deleteDeparture(Long id) {
        Departure departure =
                departureRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.DEPARTURE_NOT_FOUND));

        if (departure.getTours() != null && !departure.getTours().isEmpty()) {
            throw new AppException(ErrorCode.DEPARTURE_IN_USE);
        }

        departureRepository.delete(departure);
    }

    @Override
    public List<DepartureResponse> getAllDeparture() {
        List<Departure> departures = departureRepository.findAll();
        return departures.stream().map(departureMapper::toDepartureResponse).collect(Collectors.toList());
    }

    @Override
    public List<DepartureResponse> getAllActiveDeparture() {
        List<Departure> departures = departureRepository.findByInActiveTrue();
        return departures.stream().map(departureMapper::toDepartureResponse).collect(Collectors.toList());
    }

    @Override
    public DepartureResponse changeDepartureStatus(Long id, StatusRequest statusRequest) {
        Departure departure =
                departureRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.DEPARTURE_NOT_FOUND));
        departure.setInActive(statusRequest.getInActive());

        return departureMapper.toDepartureResponse(departureRepository.save(departure));
    }
}
