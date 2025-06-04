package com.travel.travel_booking_service.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourDetailResponse {
    private Integer id;
    private String title;
    private List<String> tourImages;
    private String status;
    private Boolean inActive;
    private Boolean isFeatured;
    private Integer maxSlots;
    private Boolean inActive;

    // Category information
    private Integer categoryId;
    private String categoryName;

    // Audit information
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
