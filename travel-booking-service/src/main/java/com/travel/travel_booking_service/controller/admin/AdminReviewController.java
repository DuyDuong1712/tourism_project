package com.travel.travel_booking_service.controller.admin;

import org.springframework.web.bind.annotation.*;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/admin/reviews")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminReviewController {

    //    ReviewService reviewService;
    //
    //    @GetMapping
    //    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getAllReviews() {
    //        return ResponseEntity.status(HttpStatus.OK).body(
    //                ApiResponse.<List<ReviewResponse>>builder()
    //                        .data(reviewService.getAllReviews())
    //                        .build()
    //        );
    //    }
    //
    //    @GetMapping("/{id}")
    //    public ResponseEntity<ApiResponse<ReviewResponse>> getReviewById(@PathVariable Long id) {
    //        return ResponseEntity.status(HttpStatus.OK).body(
    //                ApiResponse.<ReviewResponse>builder()
    //                        .data(reviewService.getReviewById(id))
    //                        .build()
    //        );
    //    }
    //
    //    @PutMapping("/{id}")
    //    public ResponseEntity<ApiResponse<ReviewResponse>> updateReview(@PathVariable Long id,
    //            @RequestBody @Valid ReviewRequest request) {
    //        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<ReviewResponse>builder()
    //                .data(reviewService.updateReview(id, request))
    //                .build());
    //    }
    //
    //    @DeleteMapping("/{id}")
    //    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
    //        reviewService.deleteReview(id);
    //        return ResponseEntity.noContent().build();
    //    }
    //
    //    @GetMapping("/search")
    //    public ResponseEntity<ApiResponse<List<ReviewResponse>>> searchReviews(@RequestParam String keyword) {
    //        return ResponseEntity.status(HttpStatus.OK).body(
    //                ApiResponse.<List<ReviewResponse>>builder()
    //                        .data(reviewService.searchReviews(keyword))
    //                        .build()
    //        );
    //    }
    //
    //    @GetMapping("/tour/{tourId}")
    //    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getReviewsByTourId(@PathVariable Long tourId) {
    //        return ResponseEntity.status(HttpStatus.OK).body(
    //                ApiResponse.<List<ReviewResponse>>builder()
    //                        .data(reviewService.getReviewsByTourId(tourId))
    //                        .build()
    //        );
    //    }
    //
    //    @GetMapping("/user/{userId}")
    //    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getReviewsByUserId(@PathVariable Long userId) {
    //        return ResponseEntity.status(HttpStatus.OK).body(
    //                ApiResponse.<List<ReviewResponse>>builder()
    //                        .data(reviewService.getReviewsByUserId(userId))
    //                        .build()
    //        );
    //    }
}
