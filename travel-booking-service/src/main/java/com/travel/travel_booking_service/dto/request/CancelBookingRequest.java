package com.travel.travel_booking_service.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CancelBookingRequest {
    private Long penaltyAmount;
    private Long refundAmount;
    private String reason; // Optional reason for cancellation
}
