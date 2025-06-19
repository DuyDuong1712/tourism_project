package com.travel.travel_booking_service.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CancelBookingRequest {
    private String cancelReason; // Optional reason for cancellation
}
