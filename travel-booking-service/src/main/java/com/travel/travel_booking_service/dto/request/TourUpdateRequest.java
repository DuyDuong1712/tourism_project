package com.travel.travel_booking_service.dto.request;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourUpdateRequest {
    @NotNull(message = "Tour ID is required")
    private Integer id;

    private String title;
    private String description;

    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private Long price;

    private String duration;

    @Min(value = 0, message = "Max slots must be greater than or equal to 0")
    private Integer maxSlots;

    private Integer categoryId;
    private Boolean inActive;

    // Additional fields for update
    private List<Integer> destinationIds;
    private List<Integer> hotelIds;
    private List<Integer> transportIds;
}
