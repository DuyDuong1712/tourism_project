package com.travel.travel_booking_service.dto.request;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequest {
    @NotBlank(message = "ROLE_CODE_REQUIRED")
    private String code;

    private String description;

    Set<String> permissions = new HashSet<>();
}
