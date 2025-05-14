package com.travel.travel_booking_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    private static final long serialVersionUID = -863164858986274318L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inActive", columnDefinition = "TINYINT default 1")
    private Boolean inActive = true;

    @Column(name = "isDelete", columnDefinition = "TINYINT default 0")
    private Boolean isDelete = false;

    @CreatedDate
    @Column(name = "createddate", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @CreatedBy
    @Column(name = "createdby", nullable = true)
    private String createdBy;

    @LastModifiedDate
    @Column(name = "modifieddate", nullable = true)
    private LocalDateTime modifiedDate;

    @LastModifiedBy
    @Column(name = "modifiedby", nullable = true)
    private String modifiedBy;
}
