package com.travel.travel_booking_service.enums;

import java.util.LinkedHashMap;
import java.util.Map;

public enum Gender {
    MALE("Nam"),
    FEMALE("Nữ");

    private final String description;

    Gender(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Map<String, String> getType() {
        Map<String, String> listType = new LinkedHashMap<>();
        for (Gender item : Gender.values()) {
            listType.put(item.toString(), item.description);
        }
        return listType;
    }
}
