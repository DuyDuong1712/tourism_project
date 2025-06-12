package com.travel.travel_booking_service.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "customer_info")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerInfo extends BaseEntity {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "gender")
    private String gender;

    @Column(name = "date_of_birth")
    private LocalDateTime dateOfBirth;

    @Column(name = "id_card", length = 20)
    private String idCard;

    @Column(length = 20)
    private String passport;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(nullable = false, columnDefinition = "VARCHAR(100) DEFAULT 'VietNam'")
    private String country = "VietNam";
}
