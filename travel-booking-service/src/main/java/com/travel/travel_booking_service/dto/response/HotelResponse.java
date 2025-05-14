package com.travel.travel_booking_service.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class HotelResponse {
    private Integer id;
    private String name;
    private String address;
    private String city;
    private String country;
    private Integer starRating;
    private String description;
    private String image;
    private Integer status;
    private LocalDateTime createddate;
    private LocalDateTime modifieddate;
    private String createdby;
    private String modifiedby;
} 