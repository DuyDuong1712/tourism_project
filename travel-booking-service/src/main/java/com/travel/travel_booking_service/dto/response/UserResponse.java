package com.travel.travel_booking_service.dto.response;

import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Long id;
    String username;
    String fullname;
    String phone;
    String email;
    String profileImg;
    String role;
    Boolean inActive;
    LocalDateTime createdDate;
}
