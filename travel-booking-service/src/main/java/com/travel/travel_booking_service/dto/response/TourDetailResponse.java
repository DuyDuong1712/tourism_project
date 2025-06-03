package com.travel.travel_booking_service.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourDetailResponse {
    private Integer id;
    private String title;
    private String description;
    private Long price;
    private String duration;
    private Integer maxSlots;
    private Boolean inActive;

    // Category information
    private Integer categoryId;
    private String categoryName;

    // Related information
    //    private List<DestinationResponse> destinations;
    //    private List<HotelResponse> hotels;
    //    private List<TransportResponse> transports;
    //    private List<TripResponse> trips;

    // Audit information
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
