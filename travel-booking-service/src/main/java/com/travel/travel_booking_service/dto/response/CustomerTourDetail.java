package com.travel.travel_booking_service.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerTourDetail {
    private Long id;
    private Long adultPrice;
    private Long childrenPrice;
    private Long childPrice;
    private Long babyPrice;
    private Long singleRoomSupplementPrice;
    private Integer stock;
    private Integer bookedSlots;
    private Integer remainingSlots;
    private Integer discount;
    private LocalDateTime dayStart;
    private LocalDateTime dayReturn;
    private Long duration;
    private String status;
}
