package com.travel.travel_booking_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travel.travel_booking_service.entity.TourInformation;

@Repository
public interface TourInformationRepository extends JpaRepository<TourInformation, Long> {}
