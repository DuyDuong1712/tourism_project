package com.travel.travel_booking_service.entity;

import com.travel.travel_booking_service.enums.TripStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "trip")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Trip extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guide_id")
    private User guide;

    @Column(name = "startdate", nullable = false)
    private LocalDate startDate;

    @Column(name = "enddate", nullable = false)
    private LocalDate endDate;

    @Column(name = "actual_price")
    private BigInteger actualPrice;

    @Column(name = "available_slots")
    private Integer availableSlots;

    @Column(name = "meeting_point")
    private String meetingPoint;

    @Column(name = "meeting_time")
    private LocalTime meetingTime;

    @Column
    @Enumerated(EnumType.STRING)
    private TripStatus status = TripStatus.SCHEDULED;

    @Column(name = "inActive")
    private int inActive = 1;

    @Column(name = "isDelete")
    private int isDelete = 0;
} 