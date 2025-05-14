package com.travel.travel_booking_service.dto.request;

import lombok.Data;

@Data
public class NotificationRequest {
    private Integer userId;
    private String title;
    private String message;
    private String type;
    private Boolean readStatus;
} 