package com.travel.travel_booking_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travel.travel_booking_service.entity.Transport;

@Repository
public interface TransportRepository extends JpaRepository<Transport, Long> {
    List<Transport> findAll();

    List<Transport> findByName(String name);

    List<Transport> findByNameIgnoreCase(String name);

    List<Transport> findByInActiveTrue();

    List<Transport> findByInActiveFalse();

    List<Transport> findByType(String type);

    List<Transport> findByBrand(String brand);

    Long countByInActiveTrue();

    Long countByInActiveFalse();
}
