package com.travel.travel_booking_service.enums;

public enum TourDetailStatus {
    SCHEDULED("Đã lên lịch"),
    IN_PROGRESS("Đang diễn ra"),
    COMPLETED("Đã hoàn thành"),
    CANCELLED("Đã hủy");

    private final String description;

    TourDetailStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
