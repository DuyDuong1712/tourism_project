package com.travel.travel_booking_service.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourResponse {
    private Integer id;
    private String title;
    private List<String> tourImages;
    private
    private String description;
    private Long price;
    private String duration;
    private Integer maxSlots;
    private Boolean inActive;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
