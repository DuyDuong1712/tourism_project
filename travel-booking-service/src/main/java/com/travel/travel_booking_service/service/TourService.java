package com.travel.travel_booking_service.service;

import com.travel.travel_booking_service.dto.TourDTO;
import com.travel.travel_booking_service.dto.TourSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TourService {
    TourDTO createTour(TourDTO tourDTO);
    TourDTO updateTour(Long id, TourDTO tourDTO);
    void deleteTour(Long id);
    TourDTO getTourById(Long id);
    Page<TourDTO> getAllTours(Pageable pageable);
    Page<TourDTO> searchTours(TourSearchDTO searchDTO, Pageable pageable);
    List<TourDTO> getToursByCategory(Long categoryId);
    List<TourDTO> getToursByDestination(Long destinationId);
    List<TourDTO> getUpcomingTours();
    List<TourDTO> getPopularTours();
    void updateTourStatus(Long id, String status);
    boolean isTourAvailable(Long id);
    void updateTourCapacity(Long id, int newCapacity);
} 