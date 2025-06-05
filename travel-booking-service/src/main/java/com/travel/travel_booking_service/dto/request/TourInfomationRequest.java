package com.travel.travel_booking_service.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TourInfomationRequest {
    private String attractions;
    private String cuisine;
    private String idealTime;
    private String vehicle;
    private String promotion;
    private String suitableObject;
}
