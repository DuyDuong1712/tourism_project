package com.travel.travel_booking_service.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.travel_booking_service.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {

    //    private final ReviewRepository reviewRepository;
    //    private final TourRepository tourRepository;
    //    private final UserRepository userRepository;
    //    private final BookingRepository bookingRepository;
    //    private final ReviewMapper reviewMapper;
    //
    //    @Override
    //    public ReviewDTO createReview(ReviewRequestDTO reviewRequest) {
    //        validateReview(reviewRequest);
    //
    //        Tour tour = tourRepository.findById(reviewRequest.getTourId())
    //                .orElseThrow(() -> new ResourceNotFoundException("Tour", "id", reviewRequest.getTourId()));
    //
    //        User user = userRepository.findById(reviewRequest.getUserId())
    //                .orElseThrow(() -> new ResourceNotFoundException("User", "id", reviewRequest.getUserId()));
    //
    //        if (hasUserReviewedTour(user.getId(), tour.getId())) {
    //            throw new DuplicateResourceException("Review", "tourId", tour.getId());
    //        }
    //
    //        if (!hasUserCompletedTour(user.getId(), tour.getId())) {
    //            throw new BusinessException(ErrorCode.REVIEW_NOT_ALLOWED);
    //        }
    //
    //        Review review = reviewMapper.toEntity(reviewRequest);
    //        review.setTour(tour);
    //        review.setUser(user);
    //        review.setCreatedAt(LocalDateTime.now());
    //
    //        Review savedReview = reviewRepository.save(review);
    //        return reviewMapper.toDTO(savedReview);
    //    }
    //
    //    @Override
    //    public ReviewDTO updateReview(Long id, ReviewDTO reviewDTO) {
    //        Review existingReview = reviewRepository.findById(id)
    //                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", id));
    //
    //        reviewMapper.updateEntityFromDTO(reviewDTO, existingReview);
    //        existingReview.setUpdatedAt(LocalDateTime.now());
    //
    //        Review updatedReview = reviewRepository.save(existingReview);
    //        return reviewMapper.toDTO(updatedReview);
    //    }
    //
    //    @Override
    //    public void deleteReview(Long id) {
    //        if (!reviewRepository.existsById(id)) {
    //            throw new ResourceNotFoundException("Review", "id", id);
    //        }
    //        reviewRepository.deleteById(id);
    //    }
    //
    //    @Override
    //    @Transactional(readOnly = true)
    //    public ReviewDTO getReviewById(Long id) {
    //        Review review = reviewRepository.findById(id)
    //                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", id));
    //        return reviewMapper.toDTO(review);
    //    }
    //
    //    @Override
    //    @Transactional(readOnly = true)
    //    public Page<ReviewDTO> getAllReviews(Pageable pageable) {
    //        return reviewRepository.findAll(pageable)
    //                .map(reviewMapper::toDTO);
    //    }
    //
    //    @Override
    //    @Transactional(readOnly = true)
    //    public Page<ReviewDTO> getReviewsByTourId(Long tourId, Pageable pageable) {
    //        return reviewRepository.findByTourId(tourId, pageable)
    //                .map(reviewMapper::toDTO);
    //    }
    //
    //    @Override
    //    @Transactional(readOnly = true)
    //    public Page<ReviewDTO> getReviewsByUserId(Long userId, Pageable pageable) {
    //        return reviewRepository.findByUserId(userId, pageable)
    //                .map(reviewMapper::toDTO);
    //    }
    //
    //    @Override
    //    @Transactional(readOnly = true)
    //    public List<ReviewDTO> getRecentReviews() {
    //        return reviewRepository.findTop10ByOrderByCreatedAtDesc().stream()
    //                .map(reviewMapper::toDTO)
    //                .collect(Collectors.toList());
    //    }
    //
    //    @Override
    //    @Transactional(readOnly = true)
    //    public double getAverageRating(Long tourId) {
    //        return reviewRepository.calculateAverageRating(tourId);
    //    }
    //
    //    @Override
    //    @Transactional(readOnly = true)
    //    public boolean hasUserReviewedTour(Long userId, Long tourId) {
    //        return reviewRepository.existsByUserIdAndTourId(userId, tourId);
    //    }
    //
    //    @Override
    //    public void validateReview(ReviewRequestDTO reviewRequest) {
    //        if (reviewRequest.getRating() < 1 || reviewRequest.getRating() > 5) {
    //            throw new BusinessException(ErrorCode.INVALID_REVIEW_RATING);
    //        }
    //
    //        if (reviewRequest.getComment() == null || reviewRequest.getComment().trim().isEmpty()) {
    //            throw new BusinessException(ErrorCode.VALIDATION_ERROR, "Review comment is required");
    //        }
    //    }
    //
    //    @Override
    //    @Transactional(readOnly = true)
    //    public List<ReviewDTO> getTopRatedReviews(Long tourId) {
    //        return reviewRepository.findTop5ByTourIdOrderByRatingDesc(tourId).stream()
    //                .map(reviewMapper::toDTO)
    //                .collect(Collectors.toList());
    //    }
    //
    //    private boolean hasUserCompletedTour(Long userId, Long tourId) {
    //        List<Booking> bookings = bookingRepository.findByUserIdAndTourId(userId, tourId);
    //        return bookings.stream()
    //                .anyMatch(booking -> "COMPLETED".equals(booking.getStatus()));
    //    }
}
