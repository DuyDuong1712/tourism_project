package com.travel.travel_booking_service.enums;

public enum NotificationType {
    BOOKING("Thông báo đặt chỗ"),
    PAYMENT("Thông báo thanh toán"),
    SYSTEM("Thông báo hệ thống"),
    PROMOTION("Thông báo khuyến mãi"),
    REMINDER("Thông báo nhắc nhở");

    private final String description;

    NotificationType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
