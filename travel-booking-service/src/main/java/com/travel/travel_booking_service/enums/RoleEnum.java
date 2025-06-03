package com.travel.travel_booking_service.enums;

import java.util.LinkedHashMap;
import java.util.Map;

public enum RoleEnum {
    ADMIN("Quản trị viên"),
    MANAGER("Quản lý có quyền xem và quản lý các đơn hàng"),
    STAFF("Nhân viên chỉ có quyền xem dashboard"),
    CUSTOMER("Khách hàng");

    private final String description;

    RoleEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Map<String, String> getType() {
        Map<String, String> listType = new LinkedHashMap<>();
        for (RoleEnum item : RoleEnum.values()) {
            listType.put(item.toString(), item.description);
        }
        return listType;
    }
}
