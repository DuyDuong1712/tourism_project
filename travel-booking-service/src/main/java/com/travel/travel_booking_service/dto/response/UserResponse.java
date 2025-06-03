package com.travel.travel_booking_service.dto.response;

import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    private Long id;
    private String username;
    private String fullname;
    private String phone;
    private String email;
    private String address;
    private LocalDate dateOfBirth;
    private String gender;
    private String profileImg;
    RoleResponse role;
}
