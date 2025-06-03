package com.travel.travel_booking_service.repository;

import org.springframework.data.repository.CrudRepository;

import com.travel.travel_booking_service.entity.InvalidatedToken;

public interface InvalidatedTokenRepository extends CrudRepository<InvalidatedToken, String> {}
