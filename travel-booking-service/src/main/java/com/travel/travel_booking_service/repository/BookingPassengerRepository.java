package com.travel.travel_booking_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travel.travel_booking_service.entity.BookingPassenger;

@Repository
public interface BookingPassengerRepository extends JpaRepository<BookingPassenger, Long> {}
