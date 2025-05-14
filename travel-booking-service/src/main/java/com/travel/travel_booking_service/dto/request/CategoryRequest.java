package com.travel.travel_booking_service.dto.request;

import lombok.Data;

@Data
public class CategoryRequest {
    private String name;
    private String description;
    private String image;
    private Integer status;
} 