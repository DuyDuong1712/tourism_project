package com.travel.travel_booking_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travel.travel_booking_service.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Override
    List<Category> findAll();

    Optional<Category> findByNameIgnoreCase(String name);

    List<Category> findByInActiveTrue();

    List<Category> findByInActiveFalse();
    ;

    boolean existsByNameIgnoreCase(String name);
}
