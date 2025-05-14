package com.travel.travel_booking_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @Column(name = "booking_date")
    private LocalDateTime bookingDate;

    @Column(name = "adults", nullable = false)
    private Integer adults;

    @Column(name = "children")
    private Integer children;

    @Column(name = "number_of_people", nullable = false)
    private Integer numberOfPeople;

    @Column(name = "total_price", nullable = false)
    private BigInteger totalPrice;

    @Column(name = "booking_status")
    @Enumerated(EnumType.STRING)
    private String bookingStatus;

    @Column(name = "is_delete")
    private Boolean isDelete = false;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private String paymentStatus;

    @Column(name = "cancellation_reason", columnDefinition = "TEXT")
    private String cancellationReason;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @OneToMany(mappedBy = "booking", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<BookingManagement> bookingManagements = new ArrayList<>();

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private Review review;

    @OneToMany(mappedBy = "booking", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Notification> notifications = new ArrayList<>();

    @PrePersist
    @PreUpdate
    public void calculateNumberOfPeople() {
        if (adults == null) adults = 1;
        if (children == null) children = 0;
        this.numberOfPeople = (adults + children);
    }
}