package com.travel.travel_booking_service.enums;

public enum RefundMethod {
    CASH("Tiền mặt"),
    BANK_TRANSFER("Ngân hàng"),
    VNPAY("VNPay"),
    MOMO("Momo");

    private final String description;

    RefundMethod(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
