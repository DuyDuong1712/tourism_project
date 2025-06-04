package com.travel.travel_booking_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourScheduleRequest {
    private int day;
    private String title;
    private String information;
}
