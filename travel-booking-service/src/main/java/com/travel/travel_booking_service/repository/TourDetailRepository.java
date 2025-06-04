package com.travel.travel_booking_service.repository;

import com.travel.travel_booking_service.entity.Tour;
import com.travel.travel_booking_service.entity.TourDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourDetailRepository  extends JpaRepository<TourDetail, Long> {
}
