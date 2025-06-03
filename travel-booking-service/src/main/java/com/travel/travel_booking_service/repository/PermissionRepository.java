package com.travel.travel_booking_service.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travel.travel_booking_service.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByCode(String code);

    Optional<Permission> findByCodeIgnoreCase(String name);

    boolean existsByCodeIgnoreCase(String code);

    List<Permission> findByIdIn(Collection<Long> ids);

    List<Permission> findByCodeIn(Collection<String> codes);
}
