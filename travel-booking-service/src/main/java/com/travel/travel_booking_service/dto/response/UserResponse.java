package com.travel.travel_booking_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
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
