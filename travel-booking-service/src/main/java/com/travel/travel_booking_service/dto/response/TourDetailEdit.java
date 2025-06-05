package com.travel.travel_booking_service.dto.response;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TourDetailEdit {
    private Long id;
    private Long TourId;
    private Long adultPrice;
    private Long childrenPrice;
    private Long childPrice;
    private Long babyPrice;
    private Long singleRoomSupplementPrice;
    private Integer stock; // Changed to String to match JSON
    private Integer discount; // Changed to String to match JSON
    private LocalDateTime dayStart;
    private LocalDateTime dayReturn;
}
