package com.travel.travel_booking_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerInfoResponse {
    Long id;
    String fullname;
    String phone;
    String email;
    String address;
    String gender;
    String date_of_birth;
    String id_card;
    String passport;
    String country;
}
