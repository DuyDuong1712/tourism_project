package com.travel.travel_booking_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerFavoriteViewResponse {
    Long id;
    Long tourId;
    String title;
    String destination;
    String departure;
    String duration;
    String category;
    Long price;
    String tourImages;

}
