package com.travel.travel_booking_service.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "destinations")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Destination extends BaseEntity {

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "image")
    private String image;

    @Column(name = "in_active", columnDefinition = "TINYINT DEFAULT 1")
    private Boolean inActive;

    // Quan hệ tự tham chiếu: Một Destination có thể có nhiều Destination con
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private List<Destination> children = new ArrayList<>();

    // Quan hệ tự tham chiếu: Một Destination có một Destination cha
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private Destination parent;

    @OneToMany(
            mappedBy = "destination",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true)
    private List<Tour> tours = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (inActive == null) inActive = true;
    }
}
