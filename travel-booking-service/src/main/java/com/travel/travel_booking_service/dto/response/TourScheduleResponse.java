package com.travel.travel_booking_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TourScheduleResponse {
    private Long id;
    private Long TourId;
    private int day;
    private String title;
    private String information;
}
