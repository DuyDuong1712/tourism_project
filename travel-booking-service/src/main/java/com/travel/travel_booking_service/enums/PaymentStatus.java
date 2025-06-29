package com.travel.travel_booking_service.enums;

public enum PaymentStatus {
    PENDING("Chờ xử lý"),
    PAID("Đã thanh toán"),
    REFUNDED("Đã hoàn tiền"),
    FAILED("Thất bại");

    private final String description;

    PaymentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
