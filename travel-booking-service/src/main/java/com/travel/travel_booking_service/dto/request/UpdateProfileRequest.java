package com.travel.travel_booking_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileRequest {
    private String fullName;
    private String phoneNumber;
    private String address;
    private String gender;
    private String date_of_birth;
    private String id_card;
    private String passport;
    private String country;
}
