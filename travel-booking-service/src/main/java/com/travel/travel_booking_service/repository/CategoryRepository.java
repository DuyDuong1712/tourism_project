package com.travel.travel_booking_service.repository;

import com.travel.travel_booking_service.entity.Category;
import com.travel.travel_booking_service.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
