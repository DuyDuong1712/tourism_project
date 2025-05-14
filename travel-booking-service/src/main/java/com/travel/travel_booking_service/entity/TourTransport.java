package com.travel.travel_booking_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tour_transports")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourTransport extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;

    @ManyToOne
    @JoinColumn(name = "transport_id", nullable = false)
    private Transport transport;
}