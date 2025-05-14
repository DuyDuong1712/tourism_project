package com.travel.travel_booking_service.dto.request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class UserRequest {
    private String username;
    private String password;
    private String fullname;
    private String phone;
    private String email;
    private String address;
    private LocalDate dateofbirth;
    private String gender;
    private String profileimg;
} 