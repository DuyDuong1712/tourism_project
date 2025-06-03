package com.travel.travel_booking_service.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "tour_departures")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TourDeparture extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;

    @Column(name = "departure_date", nullable = false)
    private LocalDateTime departureDate;

    @Column(name = "return_date", nullable = false)
    private LocalDateTime returnDate;

    @Column(name = "meeting_point")
    private String meetingPoint;

    @Column(name = "meeting_time")
    private LocalTime meetingTime;

    @Column(name = "available_slots", nullable = false)
    private Integer availableSlots;

    @Column(name = "booked_slots")
    private Integer bookedSlots;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "status",
            columnDefinition =
                    "ENUM('scheduled', 'confirmed', 'in_progress', 'completed', 'cancelled') DEFAULT 'scheduled'")
    private TourDepartureStatus status;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @OneToOne(mappedBy = "tourDeparture", cascade = CascadeType.ALL)
    private TourPricing tourPricing;

    @OneToMany(
            mappedBy = "tourDeparture",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true)
    private List<Booking> bookings = new ArrayList<>();

    public enum TourDepartureStatus {
        SCHEDULED,
        CONFIRMED,
        IN_PROGRESS,
        COMPLETED,
        CANCELLED
    }
}
