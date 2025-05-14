package com.ridemart.mapper;

import com.ridemart.dto.MotorbikeDetailsRequestDto;
import com.ridemart.dto.MotorbikeDetailsResponseDto;
import com.ridemart.entity.MotorbikeDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MotorbikeDetailsMapper {
    MotorbikeDetails toEntity(MotorbikeDetailsRequestDto dto);

    @Mapping(source = "model.make.name", target = "make")
    @Mapping(source = "model.name",      target = "model")
    MotorbikeDetailsResponseDto toResponseDto(MotorbikeDetails motorbikeDetails);
}
