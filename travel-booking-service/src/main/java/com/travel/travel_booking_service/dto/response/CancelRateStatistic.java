package com.travel.travel_booking_service.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CancelRateStatistic {
    private int month;
    private int year;
    private Double cancelRate;
}
