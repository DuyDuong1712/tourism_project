package com.travel.travel_booking_service.dto.response;

import java.time.LocalDateTime;

import lombok.*;

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
