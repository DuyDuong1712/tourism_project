package com.travel.travel_booking_service.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TourDetailRequest {
    @JsonProperty("adultPrice")
    private Long adultPrice;

    @JsonProperty("childrenPrice")
    private Long childrenPrice;

    @JsonProperty("childPrice")
    private Long childPrice;

    @JsonProperty("babyPrice")
    private Long babyPrice;

    @JsonProperty("singleRoomSupplementPrice")
    private Long singleRoomSupplementPrice;

    @JsonProperty("stock")
    private Integer stock; // Changed to String to match JSON

    @JsonProperty("discount")
    private Integer discount; // Changed to String to match JSON

    @JsonProperty("dayStart")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime dayStart;

    @JsonProperty("dayReturn")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime dayReturn;

    @JsonProperty("dateRange")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private List<LocalDateTime> dateRange;
}

// dateRange: DateStart DayReturn
