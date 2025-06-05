package com.travel.travel_booking_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travel.travel_booking_service.entity.Tour;
import com.travel.travel_booking_service.entity.TourDetail;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {

    List<Tour> findByTourDetails(List<TourDetail> tourDetails);
}
