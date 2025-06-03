package com.travel.travel_booking_service.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.travel.travel_booking_service.entity.Departure;

public interface DepartureRepository extends CrudRepository<Departure, Long> {
    List<Departure> findAll();

    List<Departure> findByName(String name);

    List<Departure> findByNameIgnoreCase(String name);

    List<Departure> findByInActiveTrue();

    List<Departure> findByInActiveFalse();

    boolean existsByNameIgnoreCase(String name);

    boolean existsByCodeIgnoreCase(String code);
}
