package com.travel.travel_booking_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @NotBlank(message = "USERNAME_REQUIRED")
    @Size(min = 5, max = 30, message = "INVALID_USERNAME_LENGTH")
    private String username;

    @NotBlank(message = "PASSWORD_REQUIRED")
    @Size(min = 5, message = "INVALID_PASSWORD_LENGTH")
    private String password;

    @NotBlank(message = "PASSWORD_REQUIRED")
    @Size(min = 5, message = "INVALID_PASSWORD_LENGTH")
    private String retypePassword;

    @NotBlank(message = "FULLNAME_REQUIRED")
    private String fullname;

    private String address;

    @NotBlank(message = "EMAIL_REQUIRED")
    @Email(message = "EMAIL_INVALID")
    private String email;

    @NotBlank(message = "EMAIL_REQUIRED")
    @Email(message = "EMAIL_INVALID")
    private String retypeEmail;

    @NotBlank(message = "PHONENUMBER_REQUIRED")
    @Pattern(regexp = "^(\\+84|0)[35789][0-9]{8}$", message = "PHONENUMBER_INVALID")
    private String phone;
}
