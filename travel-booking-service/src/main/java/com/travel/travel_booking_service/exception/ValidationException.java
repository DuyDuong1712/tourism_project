package com.travel.travel_booking_service.exception;

import com.travel.travel_booking_service.enums.ErrorCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationException extends BusinessException {
    private final List<String> errors;

    public ValidationException(String message) {
        super(ErrorCode.VALIDATION_ERROR, message);
        this.errors = new ArrayList<>();
        this.errors.add(message);
    }

    public ValidationException(List<String> errors) {
        super(ErrorCode.VALIDATION_ERROR, "Lỗi validation");
        this.errors = errors;
    }

    public ValidationException(String message, List<String> errors) {
        super(ErrorCode.VALIDATION_ERROR, message);
        this.errors = errors;
    }
} 