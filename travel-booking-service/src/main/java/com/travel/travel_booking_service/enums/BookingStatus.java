package com.travel.travel_booking_service.enums;

import java.util.LinkedHashMap;
import java.util.Map;

public enum BookingStatus {
    PENDING("Đang chờ xử lý"),
    CONFIRMED("Đã xác nhận"),
    PROCESSING("Đang xử lý"),
    COMPLETED("Đã hoàn thành"),
    CANCELLED("Đã hủy"),
    INACTIVE("Không hoạt động");

    private final String name;

    BookingStatus(String name) {
        this.name = name;
    }

    public String getCode() {
        return name;
    }

    public static Map<String, String> getType() {
        Map<String, String> listType = new LinkedHashMap<>();
        for (BookingStatus item : BookingStatus.values()) {
            listType.put(item.toString(), item.name);
        }
        return listType;
    }
}
