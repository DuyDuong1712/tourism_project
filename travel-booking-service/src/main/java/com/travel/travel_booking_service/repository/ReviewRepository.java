package com.travel.travel_booking_service.repository;

import com.travel.travel_booking_service.entity.Hotel;
import com.travel.travel_booking_service.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
