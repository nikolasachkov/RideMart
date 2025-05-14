package com.ridemart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MakeDto {
    private Integer id;
    private String name;
    private List<ModelDto> models;
}
