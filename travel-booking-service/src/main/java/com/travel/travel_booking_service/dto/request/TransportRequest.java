package com.travel.travel_booking_service.dto.request;

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
public class TransportRequest {
    @NotBlank(message = "TRANSPORT_NAME_REQUIRED")
    private String name;

    @NotBlank(message = "TRANSPORT_TYPE_REQUIRED")
    private String type;

    @NotBlank(message = "TRANSPORT_BRAND_REQUIRED")
    private String brand;

    private String description;
}
