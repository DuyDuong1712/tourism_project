package com.travel.travel_booking_service.dto.request;

import lombok.Data;

@Data
public class TransportRequest {
    private String name;
    private String type;
    private String description;
    private String image;
    private Integer status;
} 