package com.travel.travel_booking_service.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingRequest {
    Long tourDetailId;
    Long userId;
    String fullname;
    String email;
    String phone;
    String address;
    Integer adultQuantity;
    Integer childrenQuantity;
    Integer childQuantity;
    Integer babyQuantity;
    Long singleRoomSupplementPrice;
    Integer singleRoomSupplementQuantity;
    Long originalPrice;
    Long discountAmount;
    Long finalPrice;
    String note;
    String paymentMethod;
    List<PassengerRequest> passengers;
}
