package com.travel.travel_booking_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
