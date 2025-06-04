package com.travel.travel_booking_service.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
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
    private Long discount; // Changed to String to match JSON

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

//dateRange: DateStart DayReturn
