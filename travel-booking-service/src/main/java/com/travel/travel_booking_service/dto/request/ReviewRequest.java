package com.travel.travel_booking_service.dto.request;

import lombok.Data;

@Data
public class ReviewRequest {
    private Integer userId;
    private Integer tourId;
    private Integer rating;
    private String comment;
    private String status;
} 