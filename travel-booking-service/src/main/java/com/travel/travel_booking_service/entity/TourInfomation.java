package com.travel.travel_booking_service.entity;

import jakarta.persistence.*;

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
public class TourInfomation extends BaseEntity {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id", nullable = false, unique = true)
    private Tour tour;

    @Column(name = "attractions", columnDefinition = "TEXT")
    private String attractions;

    @Column(name = "cuisine", columnDefinition = "TEXT")
    private String cuisine;

    @Column(name = "suitable_object", columnDefinition = "TEXT")
    private String suitableObject;

    @Column(name = "ideal_time", columnDefinition = "TEXT")
    private String idealTime;

    @Column(name = "what_included", columnDefinition = "TEXT")
    private String whatIncluded;

    @Column(name = "what_excluded", columnDefinition = "TEXT")
    private String whatExcluded;

    @Column(name = "important_notes", columnDefinition = "TEXT")
    private String importantNotes;

    @Column(name = "terms_conditions", columnDefinition = "TEXT")
    private String termsConditions;

    @Column(name = "cancellation_policy", columnDefinition = "TEXT")
    private String cancellationPolicy;
}
