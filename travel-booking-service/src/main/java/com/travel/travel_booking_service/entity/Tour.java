package com.travel.travel_booking_service.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
    @JsonBackReference("tour-category")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departure_id", nullable = false)
    @JsonBackReference("tour-departure")
    private Departure departure;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id", nullable = false)
    @JsonBackReference("tour-destination")
    private Destination destination;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transport_id", nullable = false)
    @JsonBackReference("tour-transport")
    private Transport transport;

    @Column(name = "is_featured", columnDefinition = "TINYINT DEFAULT 0")
    private Boolean isFeatured = false;

    @Column(name = "in_active", columnDefinition = "TINYINT DEFAULT 1")
    private Boolean inActive = true;

    @OneToOne(mappedBy = "tour", cascade = CascadeType.ALL)
    @JsonManagedReference("tour-information")
    private TourInformation tourInformation;

    @OneToMany(
            mappedBy = "tour",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true)
    @JsonManagedReference("tour-schedules")
    private List<TourSchedule> tourSchedules = new ArrayList<>();

    @OneToMany(
            mappedBy = "tour",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true)
    @JsonManagedReference("tour-images")
    private List<TourImage> tourImages = new ArrayList<>();

    @OneToMany(
            mappedBy = "tour",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true)
    @JsonManagedReference("tour-details")
    @ToString.Exclude
    private List<TourDetail> tourDetails = new ArrayList<>();

    @OneToMany(
            mappedBy = "tour",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true)
    @JsonManagedReference("tour-reviews")
    private List<Review> reviews = new ArrayList<>();

    @Override
    public String toString() {
        return "Tour{id=" + getId() + ", title=" + title + "}";
    }
}
