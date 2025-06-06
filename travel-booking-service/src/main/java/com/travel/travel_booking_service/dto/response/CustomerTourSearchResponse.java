package com.travel.travel_booking_service.dto.response;

import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerTourSearchResponse {
    Long id;
    String title;
    String description;
    String tourImages;
    Boolean inActive;
    Boolean isFeatured;
    String category;
    String departure;
    String destination;
    String transportation;
    Long price;
    List<String> dayStarts;
    String duration;
}
