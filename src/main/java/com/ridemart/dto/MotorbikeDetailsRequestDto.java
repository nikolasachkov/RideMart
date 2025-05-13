package com.ridemart.dto;

import com.ridemart.enums.EngineType;
import com.ridemart.enums.MotorbikeType;
import com.ridemart.enums.FuelSystemType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MotorbikeDetailsRequestDto {
    private Integer price;
    private String make;
    private String model;
    private Integer year;
    private Integer mileage;
    private Integer engineSize;
    private EngineType engineType;
    private MotorbikeType motorbikeType;
    private FuelSystemType fuelSystemType;
}
