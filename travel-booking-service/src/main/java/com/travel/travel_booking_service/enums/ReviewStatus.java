package com.travel.travel_booking_service.enums;

public enum ReviewStatus {
    APPROVED("Đã được phê duyệt"),
    HIDDEN("Đã bị ẩn");

    private final String description;

    ReviewStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
