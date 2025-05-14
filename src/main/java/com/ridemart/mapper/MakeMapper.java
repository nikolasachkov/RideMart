package com.ridemart.mapper;

import com.ridemart.dto.MakeDto;
import com.ridemart.entity.Make;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ModelMapper.class)
public interface MakeMapper {
    MakeDto toDto(Make make);
}
