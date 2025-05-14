package com.travel.travel_booking_service.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingResponse {
    private Integer id;
    private Integer customerId;
    private Integer tourId;
    private LocalDateTime bookingDate;
    private LocalDateTime tourDate;
    private Integer adults;
    private Integer children;
    private String bookingStatus;
    private String paymentStatus;
    private String specialRequests;
    private LocalDateTime createddate;
    private LocalDateTime modifieddate;
    private String createdby;
    private String modifiedby;
} 