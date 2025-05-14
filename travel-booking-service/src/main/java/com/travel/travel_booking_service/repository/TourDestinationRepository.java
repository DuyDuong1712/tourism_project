package com.travel.travel_booking_service.repository;

import com.travel.travel_booking_service.entity.TourDestination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourDestinationRepository extends JpaRepository<TourDestination, Long> {
} 