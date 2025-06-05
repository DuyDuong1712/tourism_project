package com.travel.travel_booking_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TourInformationResponse {
    Long id;
    Long TourId;
    String attractions;
    String cuisine;
    String idealTime;
    String vehicle;
    String promotion;
    String suitableObject;
}
