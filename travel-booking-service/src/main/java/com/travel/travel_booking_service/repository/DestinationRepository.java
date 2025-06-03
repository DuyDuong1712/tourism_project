package com.travel.travel_booking_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.travel.travel_booking_service.entity.Destination;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {
    Optional<Destination> findByNameIgnoreCase(String name);

    List<Destination> findByInActiveTrue();

    // Tìm destination theo code
    Optional<Destination> findByCode(String code);

    // Tìm destination theo tên và code
    Optional<Destination> findByNameAndCode(String name, String code);

    boolean existsByNameIgnoreCase(String name);

    boolean existsByCodeIgnoreCase(String code);

    @Query("SELECT COUNT(t) FROM Tour t WHERE t.destination = :destinationId")
    Integer countToursByDestinationId(Long destinationId);

    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

    boolean existsByCodeIgnoreCaseAndIdNot(String code, Long id);

    boolean existsByParentId(Long parentId);

    // Lấy danh sách các destination là cha của ít nhất một destination khác
    @Query("SELECT DISTINCT d.parent FROM Destination d WHERE d.parent IS NOT NULL")
    List<Destination> findParentDestinations();

    // Tìm tất cả các destination con trực tiếp của một parent_id
    List<Destination> findByParentId(Long parentId);

    // Tìm tất cả destination là node lá (không có con)
    @Query("SELECT d FROM Destination d WHERE NOT EXISTS (SELECT 1 FROM Destination c WHERE c.parentId = d.id)")
    List<Destination> findAllLeafDestinations();

    // Tìm tất cả tổ tiên của một destination (dùng truy vấn đệ quy trong MySQL 8.0+)
    @Query(
            value = "WITH RECURSIVE destination_ancestors AS ("
                    + "  SELECT id, name, parent_id, 0 AS level FROM destinations WHERE id = :id "
                    + "  UNION ALL "
                    + "  SELECT d.id, d.name, d.parent_id, da.level + 1 "
                    + "  FROM destinations d "
                    + "  INNER JOIN destination_ancestors da ON d.id = da.parent_id "
                    + ") SELECT id, name, parent_id, level FROM destination_ancestors WHERE level > 0",
            nativeQuery = true)
    List<Object[]> findAncestors(Long id);

    // Tìm tất cả con cháu của một destination (dùng truy vấn đệ quy)
    @Query(
            value = "WITH RECURSIVE destination_descendants AS ("
                    + "  SELECT id, name, parent_id, 0 AS level FROM destinations WHERE id = :id "
                    + "  UNION ALL "
                    + "  SELECT d.id, d.name, d.parent_id, dd.level + 1 "
                    + "  FROM destinations d "
                    + "  INNER JOIN destination_descendants dd ON d.parent_id = dd.id "
                    + ") SELECT id, name, parent_id, level FROM destination_descendants WHERE level > 0",
            nativeQuery = true)
    List<Object[]> findDescendants(Long id);
}
