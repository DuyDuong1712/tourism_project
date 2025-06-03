package com.travel.travel_booking_service.dto.request;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourCreationRequest {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private Long price;

    private String duration;

    @Min(value = 0, message = "Max slots must be greater than or equal to 0")
    private Integer maxSlots;

    @NotNull(message = "Category ID is required")
    private Integer categoryId;

    // Additional fields for creation
    private List<Integer> destinationIds;
    private List<Integer> hotelIds;
    private List<Integer> transportIds;
}
