package com.travel.travel_booking_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travel.travel_booking_service.entity.TourDetail;

@Repository
public interface TourDetailRepository extends JpaRepository<TourDetail, Long> {

    List<TourDetail> findByTour_Id(Long tourId);

    void deleteByTourId(Long id);
}
