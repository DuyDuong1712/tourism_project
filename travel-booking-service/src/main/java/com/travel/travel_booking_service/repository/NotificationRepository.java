package com.travel.travel_booking_service.repository;

import com.travel.travel_booking_service.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
