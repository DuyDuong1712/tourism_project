package com.travel.travel_booking_service.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "tours")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Tour extends BaseEntity {
    @Column(name = "title", nullable = false, columnDefinition = "TEXT")
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departure_id", nullable = false)
    private Departure departure;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id", nullable = false)
    private Destination destination;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transport_id", nullable = false)
    private Transport transport;

    @Column(name = "is_featured", columnDefinition = "TINYINT DEFAULT 1")
    private Boolean isFeatured;

    @Column(name = "in_active", columnDefinition = "TINYINT DEFAULT 1")
    private Boolean inActive;

    @OneToOne(mappedBy = "tour", cascade = CascadeType.ALL)
    private TourInformation tourInformation;

    @OneToMany(
            mappedBy = "tour",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true)
    private List<TourSchedule> tourSchedules = new ArrayList<>();

    @OneToMany(
            mappedBy = "tour",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true)
    private List<TourImage> tourImages = new ArrayList<>();

    @OneToMany(
            mappedBy = "tour",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true)
    private List<TourDetail> tourDetails = new ArrayList<>();


    @OneToMany(
            mappedBy = "tour",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (inActive == null) inActive = true;
        isFeatured = false;
    }
}
