package com.travel.travel_booking_service.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TourScheduleRequest {
    private int day;
    private String title;
    private String information;
}
