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
public class CustomerTourViewResponse {
    Long id;
    String title;
    String description;
    String destination;
    String departure;
    Long price;
    List<TourImageResponse> tourImages;
    List<CustomerTourDetail> tourDetails;
    TourInformationResponse tourInformation;
    List<TourScheduleResponse> tourSchedules;
}
