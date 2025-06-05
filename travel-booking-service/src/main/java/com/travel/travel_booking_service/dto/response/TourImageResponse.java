package com.travel.travel_booking_service.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TourImageResponse {
    private Long id;
    private Long TourId;
    private String ImageUrl;
}
