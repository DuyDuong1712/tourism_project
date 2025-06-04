package com.travel.travel_booking_service.repository;

import com.travel.travel_booking_service.entity.TourInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourInformationRepository extends JpaRepository<TourInformation, Long> {
}
