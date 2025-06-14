package com.travel.travel_booking_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travel.travel_booking_service.entity.CustomerInfo;
import com.travel.travel_booking_service.entity.User;

@Repository
public interface CustomerInfoRepository extends JpaRepository<CustomerInfo, Long> {

    Optional<CustomerInfo> findByUser(User user);
}
