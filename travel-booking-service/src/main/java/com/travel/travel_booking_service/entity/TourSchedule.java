package com.travel.travel_booking_service.entity;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "tour_schedules")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TourSchedule extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id", nullable = false)
    @JsonBackReference("tour-schedule")
    private Tour tour;

    @Column(name = "day", nullable = false)
    private Integer day;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "information", columnDefinition = "LONGTEXT")
    private String information;
}
