package com.travel.travel_booking_service.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.travel.travel_booking_service.validator.DobContraint;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {
    @NotBlank(message = "USERNAME_REQUIRED")
    @Size(min = 5, max = 30, message = "INVALID_USERNAME_LENGTH")
    private String username;

    @NotBlank(message = "PASSWORD_REQUIRED")
    @Size(min = 5, message = "INVALID_PASSWORD_LENGTH")
    private String password;

    @NotBlank(message = "FULLNAME_REQUIRED")
    @Size(min = 5, message = "INVALID_FULLNAME_LENGTH")
    private String fullname;

    @NotBlank(message = "PHONENUMBER_REQUIRED")
    @Pattern(regexp = "^(\\+84|0)[35789][0-9]{8}$", message = "PHONENUMBER_INVALID")
    private String phone;

    @Email(message = "EMAIL_INVALID")
    @NotBlank(message = "EMAIL_REQUIRED")
    private String email;

    private String address;

    @DobContraint(min = 18, message = "INVALID_DOB")
    private LocalDate dateOfBirth;

    private String gender;

    private String profileImg;

    String role;
}
