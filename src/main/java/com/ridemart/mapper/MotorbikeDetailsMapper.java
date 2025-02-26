package com.ridemart.mapper;

import com.ridemart.dto.MotorbikeDetailsRequestDto;
import com.ridemart.dto.MotorbikeDetailsResponseDto;
import com.ridemart.entity.MotorbikeDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MotorbikeDetailsMapper {
    MotorbikeDetails toEntity(MotorbikeDetailsRequestDto dto);
    MotorbikeDetailsResponseDto toResponseDto(MotorbikeDetails motorbikeDetails);
}
