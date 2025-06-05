package com.travel.travel_booking_service.dto.response;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TourDetailResponse {
    String status;
    Long adultPrice;
    Long childrenPrice;
    Long childPrice;
    Long babyPrice;
    Integer slots;
    Integer remainingSlots;
    Integer bookedSlots;
    LocalDateTime dayStart;
    LocalDateTime dayReturn;
}
