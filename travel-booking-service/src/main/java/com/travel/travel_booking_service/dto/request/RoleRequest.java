package com.travel.travel_booking_service.dto.request;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequest {
    @NotBlank(message = "ROLE_CODE_REQUIRED")
    private String code;

    private String description;

    Set<String> permissions = new HashSet<>();
}
