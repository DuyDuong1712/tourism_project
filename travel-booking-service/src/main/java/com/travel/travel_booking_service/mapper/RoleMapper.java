package com.travel.travel_booking_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.travel.travel_booking_service.dto.request.RoleRequest;
import com.travel.travel_booking_service.dto.response.RoleResponse;
import com.travel.travel_booking_service.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "code", expression = "java(request.getCode() != null ? request.getCode().toUpperCase() : null)")
    Role toRoleEntity(RoleRequest request);

    @Mapping(target = "code", expression = "java(request.getCode() != null ? request.getCode().toUpperCase() : null)")
    void updateRole(@MappingTarget Role role, RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
