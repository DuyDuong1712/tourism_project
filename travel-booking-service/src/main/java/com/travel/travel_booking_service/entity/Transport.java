package com.travel.travel_booking_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "transports")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transport extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "image")
    private String image;

    @Column(name = "inActive")
    private int inActive = 1;

    @Column(name = "isDelete")
    private int isDelete = 0;

    @OneToMany(mappedBy = "transport", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<TourTransport> tourTransports = new ArrayList<>();
}