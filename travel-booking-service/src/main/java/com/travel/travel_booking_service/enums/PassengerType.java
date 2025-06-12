package com.travel.travel_booking_service.enums;

import java.util.LinkedHashMap;
import java.util.Map;

public enum PassengerType {
    ADULT("Người lớn"),
    CHILDREN("Trẻ em"),
    CHILD("Trẻ nhỏ"),
    BABY("Em bé");

    private final String description;

    PassengerType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Map<String, String> getType() {
        Map<String, String> listType = new LinkedHashMap<>();
        for (PassengerType item : PassengerType.values()) {
            listType.put(item.toString(), item.description);
        }
        return listType;
    }
}
