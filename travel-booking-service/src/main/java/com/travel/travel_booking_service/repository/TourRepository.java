package com.travel.travel_booking_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travel.travel_booking_service.entity.Tour;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {

    //    @Query("SELECT t FROM Tour t WHERE t.name LIKE %:keyword% OR t.description LIKE %:keyword%")
    //    List<Tour> searchTours(@Param("keyword") String keyword);
    //
    List<Tour> findByCategoryId(Long categoryId);

    //    List<Tour> findByDestinationId(Long destinationId);
}
