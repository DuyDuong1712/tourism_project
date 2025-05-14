package com.travel.travel_booking_service.dto.request;

import lombok.Data;

@Data
public class TourImageRequest {
    private Integer tourId;
    private String cloudinaryPublicId;
    private String cloudinaryUrl;
    private Boolean isThumbnail;
    private String altText;
} 