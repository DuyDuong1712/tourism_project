package com.travel.travel_booking_service.entity;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "tour_pricing")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TourPricing extends BaseEntity {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_departure_id", nullable = false)
    private TourDeparture tourDeparture;

    // 12 trở lên
    @Column(name = "adult_price", nullable = false)
    private Long adultPrice;

    // 5-11 tuổi
    @Column(name = "children_price")
    private Long childrenPrice;

    // 2-4 tuổi
    @Column(name = "baby_price")
    private Long babyPrice;

    // 0-2 tuổi
    @Column(name = "infant_price")
    private Long infantPrice;

    @Column(name = "single_room_supplement")
    private Long singleRoomSupplement;

    @Column(name = "discount_percent")
    private Integer discountPercent;
}
