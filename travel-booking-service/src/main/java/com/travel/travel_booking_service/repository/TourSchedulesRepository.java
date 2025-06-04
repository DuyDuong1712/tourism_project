package com.travel.travel_booking_service.repository;

import com.travel.travel_booking_service.entity.Tour;
import com.travel.travel_booking_service.entity.TourSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourSchedulesRepository extends JpaRepository<TourSchedule, Long> {

}
