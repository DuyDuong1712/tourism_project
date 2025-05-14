package com.travel.travel_booking_service.service;

import com.travel.travel_booking_service.dto.DestinationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DestinationService {
    DestinationDTO createDestination(DestinationDTO destinationDTO);
    DestinationDTO updateDestination(Long id, DestinationDTO destinationDTO);
    void deleteDestination(Long id);
    DestinationDTO getDestinationById(Long id);
    Page<DestinationDTO> getAllDestinations(Pageable pageable);
    List<DestinationDTO> getPopularDestinations();
    List<DestinationDTO> searchDestinations(String keyword);
    boolean isDestinationExists(String name);
    void validateDestination(DestinationDTO destinationDTO);
    List<DestinationDTO> getDestinationsByRegion(String region);
    List<DestinationDTO> getDestinationsWithTourCount();
} 