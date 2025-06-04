package com.travel.travel_booking_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.travel.travel_booking_service.entity.Tour;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {

    //Lấy các bảng tour với tour-details
    @Query("SELECT t FROM Tour t LEFT JOIN FETCH t.tourDetails")
    List<Tour> findAllWithTourDetails();
}
