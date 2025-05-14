package com.travel.travel_booking_service.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TourAssignmentResponse {
    private Integer id;
    private Integer tourId;
    private Integer staffId;
    private String role;
    private String status;
    private LocalDateTime createddate;
    private LocalDateTime modifieddate;
    private String createdby;
    private String modifiedby;
} 