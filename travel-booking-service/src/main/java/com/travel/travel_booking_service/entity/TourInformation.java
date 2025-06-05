package com.travel.travel_booking_service.entity;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "tour_information")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TourInformation extends BaseEntity {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id", nullable = false, unique = true)
    @JsonBackReference("tour-information")
    private Tour tour;

    @Column(name = "attractions", columnDefinition = "TEXT")
    private String attractions;

    @Column(name = "cuisine", columnDefinition = "TEXT")
    private String cuisine;

    @Column(name = "suitable_object", columnDefinition = "TEXT")
    private String suitableObject;

    @Column(name = "ideal_time", columnDefinition = "TEXT")
    private String idealTime;

    @Column(name = "promotion", columnDefinition = "TEXT")
    private String promotion;

    @Column(name = "vehicle", columnDefinition = "TEXT")
    private String vehicle;
}
