package com.travel.travel_booking_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.travel.travel_booking_service.dto.request.PermissionRequest;
import com.travel.travel_booking_service.dto.response.PermissionResponse;
import com.travel.travel_booking_service.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermissionEntity(PermissionRequest request);

    void updatePermission(@MappingTarget Permission permission, PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
