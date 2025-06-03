package com.travel.travel_booking_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.travel.travel_booking_service.dto.request.UserCreationRequest;
import com.travel.travel_booking_service.dto.request.UserRequest;
import com.travel.travel_booking_service.dto.request.UserUpdateRequest;
import com.travel.travel_booking_service.dto.response.UserResponse;
import com.travel.travel_booking_service.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", ignore = true)
    User toUser(UserCreationRequest request);

    @Mapping(target = "role", ignore = true)
    User toUser(UserRequest request);

    @Mapping(target = "role", ignore = true)
    UserResponse toUserResponse(User userEntity);

    @Mapping(target = "role", ignore = true)
    void updateUser(@MappingTarget User userEntity, UserUpdateRequest request);
}
