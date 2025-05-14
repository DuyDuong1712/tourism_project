package com.travel.travel_booking_service.dto.response;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserResponse {
    private Integer id;
    private String username;
    private String fullname;
    private String phone;
    private String email;
    private String address;
    private LocalDate dateofbirth;
    private String gender;
    private String profileimg;
    private Integer status;
    private LocalDateTime createddate;
    private LocalDateTime modifieddate;
    private String createdby;
    private String modifiedby;
} 