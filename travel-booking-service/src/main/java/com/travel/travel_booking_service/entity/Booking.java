package com.travel.travel_booking_service.entity;

import java.math.BigDecimal;
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
    @JoinColumn(name = "tour_departure_id", nullable = false)
    private TourDeparture tourDeparture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @Column(name = "adult_count", columnDefinition = "INT DEFAULT 1")
    private Integer adultCount;

    @Column(name = "children_count", columnDefinition = "INT DEFAULT 0")
    private Integer childrenCount;

    @Column(name = "baby_count", columnDefinition = "INT DEFAULT 0")
    private Integer babyCount;

    @Column(name = "infant_count", columnDefinition = "INT DEFAULT 0")
    private Integer infantCount;

    @Column(name = "total_people", nullable = false)
    private Integer totalPeople;

    @Column(name = "single_room_count", columnDefinition = "INT DEFAULT 0")
    private Integer singleRoomCount;

    @Column(name = "subtotal", nullable = false, precision = 15, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "discount_amount", precision = 15, scale = 2)
    private BigDecimal discountAmount;

    @Column(name = "tax_amount", precision = 15, scale = 2)
    private BigDecimal taxAmount;

    @Column(name = "total_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "booking_status",
            columnDefinition =
                    "ENUM('pending', 'confirmed', 'processing', 'completed', 'cancelled', 'refunded') DEFAULT 'pending'")
    private BookingStatus bookingStatus;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "payment_status",
            columnDefinition = "ENUM('pending', 'paid', 'refunded', 'failed') DEFAULT 'pending'")
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", columnDefinition = "ENUM('cash', 'bank_transfer')")
    private PaymentMethod paymentMethod;

    @Column(name = "special_requests", columnDefinition = "TEXT")
    private String specialRequests;

    @Column(name = "internal_notes", columnDefinition = "TEXT")
    private String internalNotes;

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    @Column(name = "cancellation_reason", columnDefinition = "TEXT")
    private String cancellationReason;

    @Column(name = "cancelled_by")
    private String cancelledBy;

    @OneToMany(
            mappedBy = "booking",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true)
    private List<BookingPassenger> bookingPassengers = new ArrayList<>();

    @OneToMany(
            mappedBy = "booking",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true)
    private List<Payment> payments = new ArrayList<>();

    @OneToOne(
            mappedBy = "booking",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true)
    private Review review;

    public enum BookingStatus {
        PENDING,
        CONFIRMED,
        PROCESSING,
        COMPLETED,
        CANCELLED,
        REFUNDED
    }

    public enum PaymentStatus {
        PENDING,
        PAID,
        REFUNDED,
        FAILED
    }

    public enum PaymentMethod {
        CASH,
        BANK_TRANSFER
    }

    @PrePersist
    @PreUpdate
    public void calculateNumberOfPeople() {
        if (adultCount == null) adultCount = 1;
        if (childrenCount == null) childrenCount = 0;
        if (babyCount == null) babyCount = 0;
        if (infantCount == null) infantCount = 0;
        this.totalPeople = (adultCount + childrenCount + babyCount + infantCount);
    }
}
