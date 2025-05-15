package com.ridemart.mapper;

import com.ridemart.dto.ModelDto;
import com.ridemart.entity.Model;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ModelMapper {
    @Mapping(source = "make.id", target = "makeId")
    ModelDto toDto(Model model);
}
