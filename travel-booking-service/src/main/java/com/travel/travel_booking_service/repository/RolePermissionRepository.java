package com.travel.travel_booking_service.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travel.travel_booking_service.entity.Permission;
import com.travel.travel_booking_service.entity.Role;
import com.travel.travel_booking_service.entity.RolePermission;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
    void deleteByRole(Role role);

    List<RolePermission> findByRole(Role role);

    void removeRolePermissionByPermissionIn(Collection<Permission> permissions);

    boolean existsByRole_IdAndPermission_Id(Long roleId, Long permissionId);

    Optional<RolePermission> findByRoleAndPermission(Role role, Permission permission);
}
