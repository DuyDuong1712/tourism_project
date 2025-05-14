package com.travel.travel_booking_service.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TransportResponse {
    private Integer id;
    private String name;
    private String type;
    private String description;
    private String image;
    private Integer status;
    private LocalDateTime createddate;
    private LocalDateTime modifieddate;
    private String createdby;
    private String modifiedby;
} 