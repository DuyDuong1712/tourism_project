package com.travel.travel_booking_service.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.travel.travel_booking_service.enums.TourDetailStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "tour_detail")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TourDetail extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id", nullable = false)
    @ToString.Exclude
    @JsonBackReference("tour-detail")
    private Tour tour;

    @Column(name = "day_start", nullable = false)
    private LocalDateTime dayStart;

    @Column(name = "day_return", nullable = false)
    private LocalDateTime dayReturn;

    @Column(name = "stock", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer stock;

    @Column(name = "booked_slots", columnDefinition = "INT DEFAULT 0")
    private Integer bookedSlots = 0;

    @Transient
    private Integer remainingSlots;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "status",
            columnDefinition =
                    "ENUM('scheduled', 'confirmed', 'in_progress', 'completed', 'cancelled') DEFAULT 'scheduled'")
    private TourDetailStatus status;

    @Column(name = "adult_price", nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long adultPrice;

    // 5-11 tuổi
    @Column(name = "children_price")
    private Long childrenPrice = 0L;

    // 2-4 tuổi
    @Column(name = "child_price")
    private Long childPrice = 0L;

    // 0-2 tuổi
    @Column(name = "baby_price")
    private Long babyPrice = 0L;

    @Column(name = "single_room_supplement_price", columnDefinition = "BIGINT DEFAULT 0")
    private Long singleRoomSupplementPrice;

    @Column(name = "discount_percent", columnDefinition = "INT DEFAULT 0")
    private Integer discountPercent;

    //    @PrePersist
    //    @PreUpdate
    //    public void calculateRemainingSlots() {
    //        if (stock != null && bookedSlots != null) {
    //            this.remainingSlots = stock - bookedSlots;
    //        }
    //    }

    public int getRemainingSlots() {
        return (stock != null ? stock : 0) - (bookedSlots != null ? bookedSlots : 0);
    }

    @Override
    public String toString() {
        return "TourDetail{id=" + getId() + ", dayStart=" + dayStart + "}";
    }
}
