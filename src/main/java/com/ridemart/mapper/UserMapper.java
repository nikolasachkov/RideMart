package com.ridemart.mapper;

import com.ridemart.dto.RegisterRequestDto;
import com.ridemart.dto.UserDto;
import com.ridemart.dto.UserResponseDto;
import com.ridemart.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(RegisterRequestDto registerRequestDto);

    User toEntity(UserDto userDto);

    UserResponseDto toResponseDto(User user);

    List<UserResponseDto> toResponseDtoList(List<User> users);
}
