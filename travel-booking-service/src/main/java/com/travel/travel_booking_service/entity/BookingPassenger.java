package com.travel.travel_booking_service.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "booking_passengers")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingPassenger extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", columnDefinition = "ENUM('male', 'female', 'other')")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "passenger_type", nullable = false, columnDefinition = "ENUM('adult', 'children', 'baby', 'infant')")
    private PassengerType passengerType;

    @Column(name = "special_requirements", columnDefinition = "TEXT")
    private String specialRequirements;

    public enum Gender {
        MALE,
        FEMALE,
        OTHER
    }

    public enum PassengerType {
        ADULT,
        CHILDREN,
        BABY,
        INFANT
    }
}
