package com.travel.travel_booking_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.travel.travel_booking_service.entity.Tour;
import com.travel.travel_booking_service.entity.TourDetail;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long>, JpaSpecificationExecutor<Tour> {

    List<Tour> findByTourDetails(List<TourDetail> tourDetails);

    @Query(
            """
	SELECT t FROM Tour t
	WHERE (:destinationIds IS NULL OR t.destination.id IN :destinationIds)
	AND (:departureId IS NULL OR t.departure.id = :departureId)
	AND (:transportationId IS NULL OR t.transport.id = :transportationId)
	AND (:categoryId IS NULL OR t.category.id = :categoryId)
	AND (:inActive IS NULL OR t.inActive = :inActive)
	AND (:isFeatured IS NULL OR t.isFeatured = :isFeatured)
	AND (:title IS NULL OR LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%')))
""")
    List<Tour> findAllFiltered(
            @Param("destinationIds") List<Long> destinationIds,
            @Param("departureId") Long departureId,
            @Param("transportationId") Long transportationId,
            @Param("categoryId") Long categoryId,
            @Param("inActive") Boolean inActive,
            @Param("isFeatured") Boolean isFeatured,
            @Param("title") String title);

    List<Tour> findByInActiveTrue();

    Long countByInActiveTrue();

    Long countByInActiveFalse();

}
