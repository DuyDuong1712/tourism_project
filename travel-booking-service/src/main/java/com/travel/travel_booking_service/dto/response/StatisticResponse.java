package com.travel.travel_booking_service.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatisticResponse {
    private Long active;
    private Long inactive;
}
