package com.travel.travel_booking_service.dto.request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class UpdateUserRequest {
    private String fullname;
    private String phone;
    private String email;
    private String address;
    private LocalDate dateofbirth;
    private String gender;
    private String profileimg;
} 