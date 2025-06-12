package com.travel.travel_booking_service.repository;

import com.travel.travel_booking_service.entity.Booking;
import com.travel.travel_booking_service.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Modifying
    @Query("UPDATE Booking b SET b.bookingStatus = :status WHERE b.tourDetail.id = :tourDetailId")
    void updateBookingStatusByTourDetailId(@Param("tourDetailId") Long tourDetailId,
                                           @Param("status") String status);
}
