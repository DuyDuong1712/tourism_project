package com.travel.travel_booking_service.enums;

import java.util.LinkedHashMap;
import java.util.Map;

public enum PermissionEnum {
    READ_TOUR("Quyền xem danh sách và chi tiết tour"),
    CREATE_TOUR("Quyền tạo mới tour"),
    UPDATE_TOUR("Quyền cập nhật thông tin tour"),
    DELETE_TOUR("Quyền xóa tour"),

    READ_CATEGORY("Quyền xem danh sách và chi tiết danh mục"),
    CREATE_CATEGORY("Quyền tạo mới danh mục"),
    UPDATE_CATEGORY("Quyền cập nhật thông tin danh mục"),
    DELETE_CATEGORY("Quyền xóa danh mục"),

    READ_DEPARTURE("Quyền xem danh sách và chi tiết điểm khởi hành"),
    CREATE_DEPARTURE("Quyền tạo mới điểm khởi hành"),
    UPDATE_DEPARTURE("Quyền cập nhật thông tin điểm khởi hành"),
    DELETE_DEPARTURE("Quyền xóa điểm khởi hành"),

    READ_DESTINATION("Quyền xem danh sách và chi tiết điểm đến"),
    CREATE_DESTINATION("Quyền tạo mới điểm đến"),
    UPDATE_DESTINATION("Quyền cập nhật thông tin điểm đến"),
    DELETE_DESTINATION("Quyền xóa điểm đến"),

    READ_TRANSPORTATION("Quyền xem danh sách và chi tiết phương tiện di chuyển"),
    CREATE_TRANSPORTATION("Quyền tạo mới phương tiện di chuyển"),
    UPDATE_TRANSPORTATION("Quyền cập nhật thông tin phương tiện di chuyển"),
    DELETE_TRANSPORTATION("Quyền xóa phương tiện di chuyển"),

    READ_ORDER("Quyền xem danh sách và chi tiết đơn hàng"),
    UPDATE_ORDER("Quyền cập nhật trạng thái và thông tin đơn hàng"),

    READ_ROLES("Quyền xem danh sách và chi tiết vai trò"),
    CREATE_ROLES("Quyền tạo mới vai trò"),
    UPDATE_ROLES("Quyền cập nhật thông tin vai trò"),
    DELETE_ROLES("Quyền xóa vai trò"),

    READ_PERMISSIONS("Quyền xem danh sách quyền hạn trong hệ thống"),

    READ_ADMIN("Quyền xem danh sách và thông tin tài khoản quản trị"),

    READ_DASHBOARD("Quyền truy cập và xem thông tin tổng quan trên Dashboard");

    private final String description;

    PermissionEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Map<String, String> getType() {
        Map<String, String> listType = new LinkedHashMap<>();
        for (PermissionEnum item : PermissionEnum.values()) {
            listType.put(item.toString(), item.description);
        }
        return listType;
    }
}
