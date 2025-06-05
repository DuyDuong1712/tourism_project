package com.travel.travel_booking_service.dto.response;

import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionResponse {
    Long id;

    String code;

    String description;

    Boolean inActive;

    LocalDateTime createdDate;

    LocalDateTime modifiedDate;

    String createdBy;

    String modifiedBy;
}
