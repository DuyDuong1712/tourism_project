package com.travel.travel_booking_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travel.travel_booking_service.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    ////
    ////    @Query("SELECT r FROM Review r WHERE r.comment LIKE %:keyword%")
    ////    List<Review> searchReviews(@Param("keyword") String keyword);
    ////
    //    List<Review> findByTourId(Long tourId);
    //
    //    List<Review> findByUserId(Long userId);
}
