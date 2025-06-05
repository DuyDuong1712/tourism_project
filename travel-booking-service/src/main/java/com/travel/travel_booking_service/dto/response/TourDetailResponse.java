package com.travel.travel_booking_service.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TourDetailResponse {
    Long id;
    Long tourDetailId;
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
