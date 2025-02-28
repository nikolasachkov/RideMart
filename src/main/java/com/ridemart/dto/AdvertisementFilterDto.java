package com.ridemart.dto;

import com.ridemart.enums.EngineType;
import com.ridemart.enums.FuelSystemType;
import com.ridemart.enums.MotorbikeType;
import com.ridemart.enums.Make;
import lombok.Data;

@Data
public class AdvertisementFilterDto {
    private String city;
    private Make make;
    private String model;
    private Integer minYear;
    private Integer maxYear;
    private Integer minPrice;
    private Integer maxPrice;
    private Integer minMileage;
    private Integer maxMileage;
    private Integer minEngineSize;
    private Integer maxEngineSize;
    private EngineType engineType;
    private MotorbikeType motorbikeType;
    private FuelSystemType fuelSystemType;
}
