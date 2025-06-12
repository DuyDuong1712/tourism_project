package com.travel.travel_booking_service.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Booking extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_detail_id", nullable = false)
    TourDetail tourDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    User customer;

    @Column(name = "full_name", nullable = false)
    String fullName;

    @Column(name = "email", nullable = false)
    String email;

    @Column(name = "phone_number", nullable = false)
    String phoneNumber;

    @Column(name = "address", nullable = false)
    String address;

    @Column(name = "adult_count", columnDefinition = "INT DEFAULT 1")
    Integer adultCount;

    @Column(name = "children_count", columnDefinition = "INT DEFAULT 0")
    Integer childrenCount;

    @Column(name = "child_count", columnDefinition = "INT DEFAULT 0")
    Integer childCount;

    @Column(name = "baby_count", columnDefinition = "INT DEFAULT 0")
    Integer babyCount;

    @Column(name = "total_people", nullable = false, columnDefinition = "INT DEFAULT 1")
    Integer totalPeople;

    @Column(name = "single_room_count", columnDefinition = "INT DEFAULT 0")
    Integer singleRoomCount;

    @Column(name = "subtotal", nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    Long subtotal;

    @Column(name = "discount_amount", columnDefinition = "BIGINT DEFAULT 0")
    Long discountAmount;

    @Column(name = "total_amount", nullable = false)
    Long totalAmount;

    @Column(name = "note", columnDefinition = "TEXT")
    String note;


    @Column(name = "booking_status")
    String bookingStatus;

    @Column(name = "payment_status")
    String paymentStatus;

    @Column(name = "special_requests", columnDefinition = "TEXT")
    String specialRequests;

    @Column(name = "confirmed_at")
    LocalDateTime confirmedAt;

    @Column(name = "cancelled_at")
    LocalDateTime cancelledAt;

    @Column(name = "cancellation_reason", columnDefinition = "TEXT")
    String cancellationReason;

    @Column(name = "cancelled_by")
    String cancelledBy;

    @Column(name = "transaction_id")
    String transactionId;

    @Column(name = "payment_date")
    LocalDateTime paymentDate;

    @Column(name = "payment_description", columnDefinition = "TEXT")
    String paymentDescription;

    // Hoàn tiền
    @Column(name = "refund_amount", columnDefinition = "BIGINT DEFAULT 0")
    Long refundAmount;

    @Column(name = "refund_percent", columnDefinition = "INT DEFAULT 0")
    Integer refundPercent;

    @Column(name = "refund_date")
    LocalDateTime refundDate;


    @Column(name = "refund_status")
    String refundStatus;

    @Column(name = "refund_transaction_id")
    String refundTransactionId;

    @Column(name = "refund_note", columnDefinition = "TEXT")
    String refundNote;

    @OneToMany(
            mappedBy = "booking",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true)
    List<BookingPassenger> bookingPassengers = new ArrayList<>();

    @OneToOne(
            mappedBy = "booking",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true)
    Review review;

    @PrePersist
    @PreUpdate
    public void calculateNumberOfPeople() {
        if (adultCount == null) adultCount = 1;
        if (childrenCount == null) childrenCount = 0;
        if (childCount == null) childCount = 0;
        if (babyCount == null) babyCount = 0;
        this.totalPeople = adultCount + childrenCount + childCount + babyCount;
    }
}
