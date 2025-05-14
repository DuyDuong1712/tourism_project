package com.travel.travel_booking_service.exception;

import com.travel.travel_booking_service.enums.ErrorCode;

public class DuplicateResourceException extends BusinessException {
    public DuplicateResourceException(String resourceName, String fieldName, Object fieldValue) {
        super(ErrorCode.DUPLICATE_RESOURCE,
            String.format("%s đã tồn tại với %s: '%s'", resourceName, fieldName, fieldValue));
    }

    public DuplicateResourceException(ErrorCode errorCode, String resourceName, String fieldName, Object fieldValue) {
        super(errorCode,
            String.format("%s đã tồn tại với %s: '%s'", resourceName, fieldName, fieldValue));
    }
} 