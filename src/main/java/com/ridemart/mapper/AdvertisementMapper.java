package com.ridemart.mapper;

import com.ridemart.dto.AdvertisementRequestDto;
import com.ridemart.dto.AdvertisementResponseDto;
import com.ridemart.entity.Advertisement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", uses = MotorbikeDetailsMapper.class )
public interface AdvertisementMapper {

    Advertisement toEntity(AdvertisementRequestDto dto);

    @Mapping(source = "user.id", target = "userId")
    AdvertisementResponseDto toResponseDto(Advertisement advertisement);
    List<AdvertisementResponseDto> toResponseDtoList(List<Advertisement> advertisements);

    @Mapping(target = "id",               ignore = true)
    @Mapping(target = "user",             ignore = true)
    @Mapping(target = "motorbikeDetails", ignore = true)
    @Mapping(target = "photos",           ignore = true)
    @Mapping(target = "createdAt",        ignore = true)
    @Mapping(target = "updatedAt",        ignore = true)
    void updateEntityFromDto(AdvertisementRequestDto dto, @MappingTarget Advertisement ad);
}
