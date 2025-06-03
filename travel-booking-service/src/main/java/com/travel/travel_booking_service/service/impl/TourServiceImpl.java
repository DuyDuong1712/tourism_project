package com.travel.travel_booking_service.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.travel.travel_booking_service.dto.request.TourRequest;
import com.travel.travel_booking_service.dto.response.TourResponse;
import com.travel.travel_booking_service.service.TourService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TourServiceImpl implements TourService {
    @Override
    public TourResponse createTour(TourRequest request) {
        return null;
    }

    @Override
    public List<TourResponse> getAllTours() {
        return List.of();
    }

    @Override
    public TourResponse getTourById(Long id) {
        return null;
    }

    @Override
    public TourResponse updateTour(Long id, TourRequest request) {
        return null;
    }

    @Override
    public void deleteTour(Long id) {}

    @Override
    public List<TourResponse> searchTours(String keyword) {
        return List.of();
    }

    @Override
    public List<TourResponse> getToursByCategory(Long categoryId) {
        return List.of();
    }

    @Override
    public List<TourResponse> getToursByDestination(Long destinationId) {
        return List.of();
    }
    //    TourRepository tourRepository;
    //
    //    @Override
    //    @Transactional
    //    public TourResponse createTour(TourRequest request) {
    //        Tour tour = Tour.builder()
    //                .name(request.getName())
    //                .description(request.getDescription())
    //                .price(request.getPrice())
    //                .duration(request.getDuration())
    //                .categoryId(request.getCategoryId())
    //                .destinationId(request.getDestinationId())
    //                .build();
    //
    //        return convertToResponse(tourRepository.save(tour));
    //    }
    //
    //    @Override
    //    public List<TourResponse> getAllTours() {
    //        return tourRepository.findAll().stream()
    //                .map(this::convertToResponse)
    //                .collect(Collectors.toList());
    //    }
    //
    //    @Override
    //    public TourResponse getTourById(Long id) {
    //        return convertToResponse(tourRepository.findById(id)
    //                .orElseThrow(() -> new RuntimeException("Tour not found")));
    //    }
    //
    //    @Override
    //    @Transactional
    //    public TourResponse updateTour(Long id, TourRequest request) {
    //        Tour tour = tourRepository.findById(id)
    //                .orElseThrow(() -> new RuntimeException("Tour not found"));
    //
    //        tour.setName(request.getName());
    //        tour.setDescription(request.getDescription());
    //        tour.setPrice(request.getPrice());
    //        tour.setDuration(request.getDuration());
    //        tour.setCategoryId(request.getCategoryId());
    //        tour.setDestinationId(request.getDestinationId());
    //
    //        return convertToResponse(tourRepository.save(tour));
    //    }
    //
    //    @Override
    //    @Transactional
    //    public void deleteTour(Long id) {
    //        tourRepository.deleteById(id);
    //    }
    //
    //    @Override
    //    public List<TourResponse> searchTours(String keyword) {
    //        return tourRepository.searchTours(keyword).stream()
    //                .map(this::convertToResponse)
    //                .collect(Collectors.toList());
    //    }
    //
    //    @Override
    //    public List<TourResponse> getToursByCategory(Long categoryId) {
    //        return tourRepository.findByCategoryId(categoryId).stream()
    //                .map(this::convertToResponse)
    //                .collect(Collectors.toList());
    //    }
    //
    //    @Override
    //    public List<TourResponse> getToursByDestination(Long destinationId) {
    //        return tourRepository.findByDestinationId(destinationId).stream()
    //                .map(this::convertToResponse)
    //                .collect(Collectors.toList());
    //    }
    //
    //    private TourResponse convertToResponse(Tour tour) {
    //        return TourResponse.builder()
    //                .id(tour.getId())
    //                .name(tour.getName())
    //                .description(tour.getDescription())
    //                .price(tour.getPrice())
    //                .duration(tour.getDuration())
    //                .categoryId(tour.getCategoryId())
    //                .destinationId(tour.getDestinationId())
    //                .build();
    //    }
}
