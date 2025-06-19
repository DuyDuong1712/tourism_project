package com.travel.travel_booking_service.repository;

import com.travel.travel_booking_service.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByCustomer_Id(Long customerId);

    @Query("SELECT f FROM Favorite f JOIN FETCH f.tour WHERE f.customer.id = :customerId")
    List<Favorite> findByCustomer_IdWithTour(@Param("customerId") Long customerId);

    boolean existsByCustomer_IdAndTourId(Long customerId, Long tourId);

    Optional<Favorite> findByCustomer_IdAndTour_Id(Long customerId, Long tourId);
}
