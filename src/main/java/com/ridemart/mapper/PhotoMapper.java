package com.ridemart.mapper;

import com.ridemart.dto.PhotoRequestDto;
import com.ridemart.dto.PhotoResponseDto;
import com.ridemart.entity.Photo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PhotoMapper {
    Photo toEntity(PhotoRequestDto dto);
    PhotoResponseDto toResponseDto(Photo photo);
}
