package com.travel.travel_booking_service.dto.response;

import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TourEditResponse {
    String title;
    Long catagoryId;
    Long departureId;
    Long destinationId;
    Long transportId;
    String description;
    TourInformationResponse tourInformation;
    List<TourScheduleResponse> tourSchedules;
    List<TourDetailEdit> tourDetails;
    List<TourImageResponse> images;
}
