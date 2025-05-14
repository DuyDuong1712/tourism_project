package com.travel.travel_booking_service.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TourImageResponse {
    private Integer id;
    private Integer tourId;
    private String cloudinaryPublicId;
    private String cloudinaryUrl;
    private Boolean isThumbnail;
    private String altText;
    private LocalDateTime createddate;
    private LocalDateTime modifieddate;
    private String createdby;
    private String modifiedby;
} 