package com.travel.travel_booking_service.service.impl;

import com.travel.travel_booking_service.dto.TourDTO;
import com.travel.travel_booking_service.dto.TourRequestDTO;
import com.travel.travel_booking_service.entity.Category;
import com.travel.travel_booking_service.entity.Destination;
import com.travel.travel_booking_service.entity.Tour;
import com.travel.travel_booking_service.enums.ErrorCode;
import com.travel.travel_booking_service.exception.BusinessException;
import com.travel.travel_booking_service.exception.DuplicateResourceException;
import com.travel.travel_booking_service.exception.ResourceNotFoundException;
import com.travel.travel_booking_service.mapper.TourMapper;
import com.travel.travel_booking_service.repository.CategoryRepository;
import com.travel.travel_booking_service.repository.DestinationRepository;
import com.travel.travel_booking_service.repository.TourRepository;
import com.travel.travel_booking_service.service.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TourServiceImpl implements TourService {

    private final TourRepository tourRepository;
    private final CategoryRepository categoryRepository;
    private final DestinationRepository destinationRepository;
    private final TourMapper tourMapper;

    @Override
    public TourDTO createTour(TourRequestDTO tourRequest) {
        validateTour(tourRequest);

        Category category = categoryRepository.findById(tourRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", tourRequest.getCategoryId()));

        Destination destination = destinationRepository.findById(tourRequest.getDestinationId())
                .orElseThrow(() -> new ResourceNotFoundException("Destination", "id", tourRequest.getDestinationId()));

        if (isTourExists(tourRequest.getName())) {
            throw new DuplicateResourceException("Tour", "name", tourRequest.getName());
        }

        Tour tour = tourMapper.toEntity(tourRequest);
        tour.setCategory(category);
        tour.setDestination(destination);
        tour.setCreatedAt(LocalDateTime.now());
        tour.setStatus("ACTIVE");

        Tour savedTour = tourRepository.save(tour);
        return tourMapper.toDTO(savedTour);
    }

    @Override
    public TourDTO updateTour(Long id, TourRequestDTO tourRequest) {
        validateTour(tourRequest);

        Tour existingTour = tourRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tour", "id", id));

        if (!existingTour.getName().equals(tourRequest.getName()) 
                && isTourExists(tourRequest.getName())) {
            throw new DuplicateResourceException("Tour", "name", tourRequest.getName());
        }

        Category category = categoryRepository.findById(tourRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", tourRequest.getCategoryId()));

        Destination destination = destinationRepository.findById(tourRequest.getDestinationId())
                .orElseThrow(() -> new ResourceNotFoundException("Destination", "id", tourRequest.getDestinationId()));

        tourMapper.updateEntityFromDTO(tourRequest, existingTour);
        existingTour.setCategory(category);
        existingTour.setDestination(destination);
        existingTour.setUpdatedAt(LocalDateTime.now());

        Tour updatedTour = tourRepository.save(existingTour);
        return tourMapper.toDTO(updatedTour);
    }

    @Override
    public void deleteTour(Long id) {
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tour", "id", id));

        if (tour.getBookings() != null && !tour.getBookings().isEmpty()) {
            throw new BusinessException(ErrorCode.TOUR_IN_USE);
        }

        tourRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public TourDTO getTourById(Long id) {
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tour", "id", id));
        return tourMapper.toDTO(tour);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TourDTO> getAllTours(Pageable pageable) {
        return tourRepository.findAll(pageable)
                .map(tourMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TourDTO> getToursByCategory(Long categoryId, Pageable pageable) {
        return tourRepository.findByCategoryId(categoryId, pageable)
                .map(tourMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TourDTO> getToursByDestination(Long destinationId, Pageable pageable) {
        return tourRepository.findByDestinationId(destinationId, pageable)
                .map(tourMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TourDTO> getPopularTours() {
        return tourRepository.findPopularTours().stream()
                .map(tourMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TourDTO> searchTours(String keyword) {
        return tourRepository.searchTours(keyword).stream()
                .map(tourMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isTourExists(String name) {
        return tourRepository.existsByName(name);
    }

    @Override
    public void validateTour(TourRequestDTO tourRequest) {
        if (tourRequest.getName() == null || tourRequest.getName().trim().isEmpty()) {
            throw new BusinessException(ErrorCode.VALIDATION_ERROR, "Tour name is required");
        }

        if (tourRequest.getDescription() == null || tourRequest.getDescription().trim().isEmpty()) {
            throw new BusinessException(ErrorCode.VALIDATION_ERROR, "Tour description is required");
        }

        if (tourRequest.getPrice() <= 0) {
            throw new BusinessException(ErrorCode.VALIDATION_ERROR, "Tour price must be greater than zero");
        }

        if (tourRequest.getDuration() <= 0) {
            throw new BusinessException(ErrorCode.VALIDATION_ERROR, "Tour duration must be greater than zero");
        }

        if (tourRequest.getMaxGroupSize() <= 0) {
            throw new BusinessException(ErrorCode.VALIDATION_ERROR, "Tour max group size must be greater than zero");
        }
    }

    @Override
    public void updateTourStatus(Long id, String status) {
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tour", "id", id));
        tour.setStatus(status);
        tour.setUpdatedAt(LocalDateTime.now());
        tourRepository.save(tour);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TourDTO> getToursByPriceRange(double minPrice, double maxPrice) {
        return tourRepository.findByPriceBetween(minPrice, maxPrice).stream()
                .map(tourMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TourDTO> getToursByDuration(int minDuration, int maxDuration) {
        return tourRepository.findByDurationBetween(minDuration, maxDuration).stream()
                .map(tourMapper::toDTO)
                .collect(Collectors.toList());
    }
} 