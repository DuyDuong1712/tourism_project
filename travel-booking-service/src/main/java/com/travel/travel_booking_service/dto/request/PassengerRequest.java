package com.travel.travel_booking_service.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PassengerRequest {
    String fullname;
    String birthDate;
    String gender;
    boolean singleRoomSupplement;
}
