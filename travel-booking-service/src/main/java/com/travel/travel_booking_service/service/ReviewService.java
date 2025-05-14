package com.travel.travel_booking_service.service;

import com.travel.travel_booking_service.dto.ReviewDTO;
import com.travel.travel_booking_service.dto.ReviewRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {
    ReviewDTO createReview(ReviewRequestDTO reviewRequest);
    ReviewDTO updateReview(Long id, ReviewDTO reviewDTO);
    void deleteReview(Long id);
    ReviewDTO getReviewById(Long id);
    Page<ReviewDTO> getAllReviews(Pageable pageable);
    Page<ReviewDTO> getReviewsByTourId(Long tourId, Pageable pageable);
    Page<ReviewDTO> getReviewsByUserId(Long userId, Pageable pageable);
    List<ReviewDTO> getRecentReviews();
    double getAverageRating(Long tourId);
    boolean hasUserReviewedTour(Long userId, Long tourId);
    void validateReview(ReviewRequestDTO reviewRequest);
    List<ReviewDTO> getTopRatedReviews(Long tourId);
} 