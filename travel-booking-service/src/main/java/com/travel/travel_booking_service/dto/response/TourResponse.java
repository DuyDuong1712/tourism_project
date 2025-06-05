package com.travel.travel_booking_service.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TourResponse {
    Long id;
    String title;
    List<String> tourImages;
    String status;
    Boolean inActive;
    Boolean isFeatured;
    String description;
    Long adultPrice;
    Long childrenPrice;
    Long childPrice;
    Long babyPrice;
    Integer slots;
    Integer remainingSlots;
    Integer bookedSlots;
    String category;
    String departure;
    String destination;
    String transportation;
    LocalDateTime dayStart;
    LocalDateTime dayReturn;
}
