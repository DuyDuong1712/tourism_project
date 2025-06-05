package com.travel.travel_booking_service.dto.response;

import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginResponse {
    private Long id;
    private String username;
    private String fullName;
    private String avatar;
    private String role;
    private List<String> permissions;
}
