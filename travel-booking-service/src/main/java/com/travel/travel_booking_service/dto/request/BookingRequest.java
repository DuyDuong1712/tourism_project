package com.travel.travel_booking_service.dto.request;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingRequest {
    private Integer customerId;
    private Integer tourId;
    private LocalDateTime tourDate;
    private Integer adults;
    private Integer children;
    private String bookingStatus;
    private String paymentStatus;
    private String specialRequests;
} 