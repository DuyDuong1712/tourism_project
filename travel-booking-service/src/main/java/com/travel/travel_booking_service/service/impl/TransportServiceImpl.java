package com.travel.travel_booking_service.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.travel_booking_service.dto.request.StatusRequest;
import com.travel.travel_booking_service.dto.request.TransportRequest;
import com.travel.travel_booking_service.dto.response.TransportResponse;
import com.travel.travel_booking_service.entity.Transport;
import com.travel.travel_booking_service.enums.ErrorCode;
import com.travel.travel_booking_service.exception.AppException;
import com.travel.travel_booking_service.mapper.TransportMapper;
import com.travel.travel_booking_service.repository.TransportRepository;
import com.travel.travel_booking_service.service.TransportService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransportServiceImpl implements TransportService {

    TransportRepository transportRepository;
    TransportMapper transportMapper;

    @Override
    public TransportResponse createTransport(TransportRequest request) {
        Transport transport = transportMapper.toTransportEntity(request);
        return transportMapper.toTransportResponse(transportRepository.save(transport));
    }

    @Override
    public List<TransportResponse> getAllTransports() {
        List<Transport> transports = transportRepository.findAll();
        return transports.stream().map(transportMapper::toTransportResponse).collect(Collectors.toList());
    }

    @Override
    public List<TransportResponse> getAllActiveTransports() {
        List<Transport> transports = transportRepository.findByInActiveTrue();
        return transports.stream().map(transportMapper::toTransportResponse).collect(Collectors.toList());
    }

    @Override
    public TransportResponse updateTransport(Long id, TransportRequest request) {
        Transport transport =
                transportRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.TRANSPORT_NOT_FOUND));

        transportMapper.updateTransport(transport, request);

        transportRepository.save(transport);

        return transportMapper.toTransportResponse(transport);
    }

    @Override
    public void deleteTransport(Long id) {
        Transport transport =
                transportRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.TRANSPORT_NOT_FOUND));

        if (transport.getTours() != null && !transport.getTours().isEmpty()) {
            throw new AppException(ErrorCode.TRANSPORT_IN_USE);
        }

        transportRepository.delete(transport);
    }

    @Override
    public TransportResponse changeTransportStatus(Long id, StatusRequest statusRequest) {
        Transport transport =
                transportRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.TRANSPORT_NOT_FOUND));
        transport.setInActive(statusRequest.getInActive());

        return transportMapper.toTransportResponse(transportRepository.save(transport));
    }
}
