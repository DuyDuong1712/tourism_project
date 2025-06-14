package com.travel.travel_booking_service.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RevenueStatistic {
    private int month;
    private int year;
    private Long totalRevenue;
}
