package com.travel.travel_booking_service.dto.request;

import lombok.Data;

@Data
public class TourAssignmentRequest {
    private Integer tourId;
    private Integer staffId;
    private String role;
    private String status;
} 