package com.travel.travel_booking_service.dto.request;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DestinationRequest {
    private Long parentId;

    @NotBlank(message = "DESTINATION_NAME_REQUIRED")
    private String name;

    @NotBlank(message = "DESTINATION_CODE_REQUIRED")
    private String code;

    private String description;
}
