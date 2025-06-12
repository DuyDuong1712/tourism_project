package com.travel.travel_booking_service.repository;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.travel.travel_booking_service.entity.TourDetail;

@Repository
public interface TourDetailRepository extends JpaRepository<TourDetail, Long> {

    List<TourDetail> findByTour_Id(Long tourId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT td FROM TourDetail td WHERE td.id = :id")
    Optional<TourDetail> findByIdForUpdate(@Param("id") Long id);

    void deleteByTourId(Long id);
}
