package com.travel.travel_booking_service.repository;

import com.travel.travel_booking_service.entity.CustomerInfo;
import com.travel.travel_booking_service.entity.Role;
import com.travel.travel_booking_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerInfoRepository extends JpaRepository<CustomerInfo, Long> {

    Optional<CustomerInfo> findByUser(User user);
}
