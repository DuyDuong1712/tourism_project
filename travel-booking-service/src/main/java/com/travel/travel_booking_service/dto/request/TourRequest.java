package com.travel.travel_booking_service.dto.request;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TourRequest {
    private String title;
    private String description;
    private Long price;
    private String duration;
    private LocalDateTime startdate;
    private LocalDateTime enddate;
    private Integer maxSlots;
    private Integer availableSlots;
    private String status;
    private Integer categoryId;
} 