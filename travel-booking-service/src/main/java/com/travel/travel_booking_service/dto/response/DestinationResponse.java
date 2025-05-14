package com.travel.travel_booking_service.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DestinationResponse {
    private Integer id;
    private String name;
    private String city;
    private String country;
    private String description;
    private Integer status;
    private String image;
    private LocalDateTime createddate;
    private LocalDateTime modifieddate;
    private String createdby;
    private String modifiedby;
} 