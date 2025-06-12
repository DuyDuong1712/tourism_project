package com.travel.travel_booking_service.enums;

public enum RefundStatus {
    NOT_APPLICABLE("Không áp dụng"),
    PENDING("Chờ xử lý"),
    COMPLETED("Hoàn tất"),
    FAILED("Thất bại");

    private final String description;

    RefundStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
