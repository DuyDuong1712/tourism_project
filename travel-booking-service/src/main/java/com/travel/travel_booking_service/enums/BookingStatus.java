package com.travel.travel_booking_service.enums;

public enum BookingStatus {
    PENDING("Đang chờ xử lý"),
    CONFIRMED("Đã xác nhận"),
    COMPLETED("Đã hoàn thành"),
    CANCELLED("Đã hủy");

    private final String description;

    BookingStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
