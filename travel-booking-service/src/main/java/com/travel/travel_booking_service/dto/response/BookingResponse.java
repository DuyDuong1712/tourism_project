package com.travel.travel_booking_service.dto.response;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    private Long id;
    private Long tourId;
    private String tourName;
    private Long tourDetailId;
    private Long customerId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private Integer adultCount;
    private Integer childrenCount;
    private Integer childCount;
    private Integer babyCount;
    private Integer totalPeople;
    private Integer singleRoomCount;
    private Long adultPrice;
    private Long childrenPrice;
    private Long childPrice;
    private Long babyPrice;
    private Long subtotal;
    private Long discountAmount;
    private Long totalAmount;
    private String note;
    private String bookingStatus; // PENDING, CONFIRMED, COMPLETED, CANCELLED
    private String paymentStatus; // PENDING, PAID, REFUNDED, FAILED
    private LocalDateTime confirmedAt;
    private LocalDateTime cancelledAt;
    private String cancellationReason;
    private String cancelledBy;
    private String transactionId;
    private LocalDateTime paymentDate;
    private String paymentDescription;
    private Long refundAmount;
    private Integer refundPercent;
    private LocalDateTime refundDate;
    private String refundStatus; // NOT_APPLICABLE, PENDING, COMPLETED, FAILED
    private String refundTransactionId;
    private String refundNote;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String createdBy;
    private String modifiedBy;
    private LocalDateTime startDate;
    private LocalDateTime returnDate;
}
