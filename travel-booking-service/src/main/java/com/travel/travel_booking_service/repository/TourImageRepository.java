package com.travel.travel_booking_service.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.travel.travel_booking_service.entity.Tour;
import com.travel.travel_booking_service.entity.TourImage;

public interface TourImageRepository extends CrudRepository<TourImage, Long> {
    List<TourImage> getByTour(Tour tour);

    List<TourImage> getByTour_Id(Long tourId);

    void deleteByTourId(Long id);
}
