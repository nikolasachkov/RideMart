package com.ridemart.mapper;

import com.ridemart.dto.AdvertisementRequestDto;
import com.ridemart.dto.AdvertisementResponseDto;
import com.ridemart.entity.Advertisement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", uses = MotorbikeDetailsMapper.class )
public interface AdvertisementMapper {

    Advertisement toEntity(AdvertisementRequestDto dto);

    @Mapping(source = "user.id", target = "userId")
    AdvertisementResponseDto toResponseDto(Advertisement advertisement);
    List<AdvertisementResponseDto> toResponseDtoList(List<Advertisement> advertisements);
}
