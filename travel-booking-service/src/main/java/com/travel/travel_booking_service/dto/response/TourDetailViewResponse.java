package com.travel.travel_booking_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TourDetailViewResponse {
    String title;
    String category;
    String departure;
    String destination;
    String transportation;
    String description;
    TourInformationResponse tourInformation;
    List<TourScheduleResponse> tourSchedules;
    List<TourDetailEdit> tourDetails;
    List<TourImageResponse> images;
    String createdBy;
    Boolean inActive;
}
