package com.travel.travel_booking_service.exception;

import com.travel.travel_booking_service.enums.ErrorCode;

public class ResourceNotFoundException extends BusinessException {
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(ErrorCode.RESOURCE_NOT_FOUND, 
            String.format("%s không tìm thấy với %s: '%s'", resourceName, fieldName, fieldValue));
    }

    public ResourceNotFoundException(ErrorCode errorCode, String resourceName, String fieldName, Object fieldValue) {
        super(errorCode, 
            String.format("%s không tìm thấy với %s: '%s'", resourceName, fieldName, fieldValue));
    }
} 