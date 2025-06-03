package com.travel.travel_booking_service.dto.request;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartureRequest {
    @NotBlank(message = "DEPARTURE_NAME_REQUIRED")
    private String name;

    @NotBlank(message = "DEPARTURE_CODE_REQUIRED")
    private String code;

    @NotBlank(message = "DEPARTURE_ADDRESS_REQUIRED")
    private String address;
}
