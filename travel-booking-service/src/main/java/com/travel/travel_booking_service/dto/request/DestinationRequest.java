package com.travel.travel_booking_service.dto.request;

import lombok.Data;

@Data
public class DestinationRequest {
    private String name;
    private String city;
    private String country;
    private String description;
    private Integer status;
    private String image;
} 