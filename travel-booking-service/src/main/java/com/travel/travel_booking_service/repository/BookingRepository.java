package com.travel.travel_booking_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.travel.travel_booking_service.entity.Booking;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Modifying
    @Query("UPDATE Booking b SET b.bookingStatus = :status WHERE b.tourDetail.id = :tourDetailId")
    void updateBookingStatusByTourDetailId(@Param("tourDetailId") Long tourDetailId, @Param("status") String status);

    Booking findByTourDetail_Id(Long tourDetailId);

    List<Booking> findByCustomer_Id(Long customerId);
}
