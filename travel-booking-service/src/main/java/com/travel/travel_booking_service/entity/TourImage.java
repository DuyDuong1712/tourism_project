package com.travel.travel_booking_service.entity;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "tour_images")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TourImage extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id", nullable = false)
    @JsonBackReference("tour-image")
    private Tour tour;

    @Column(name = "cloudinary_public_id")
    private String cloudinaryPublicId;

    @Column(name = "cloudinary_url", nullable = false, length = 500)
    private String cloudinaryUrl;

    @Column(name = "alt_text")
    private String altText;
}
