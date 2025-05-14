package com.travel.travel_booking_service.dto.request;

import lombok.Data;

@Data
public class HotelRequest {
    private String name;
    private String address;
    private String city;
    private String country;
    private Integer starRating;
    private String description;
    private String image;
    private Integer status;
} 