package com.travel.travel_booking_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerViewBookingResponse {
    private Long id;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String customerAddress;

    private String tourName;
    private String departure;
    private String destination;
    private String dayStart;
    private String dayReturn;

    private Integer adultQuantity;
    private Integer childrenQuantity;
    private Integer childQuantity;
    private Integer babyQuantity;
    private Integer totalPeople;
    private Long totalAmount;
    private String note;
    private String bookingStatus;
    private String paymentDate;
    private String transcationId;
}
