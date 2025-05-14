package com.travel.travel_booking_service.repository;

import com.travel.travel_booking_service.entity.BookingManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingManagementRepository extends JpaRepository<BookingManagement, Long> {
} 