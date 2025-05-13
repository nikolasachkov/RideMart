package com.ridemart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementRequestDto {
    private String title;
    private String description;
    private String city;
    private String street;
    private String streetNumber;
    private MotorbikeDetailsRequestDto motorbikeDetails;
    private List<String> photoUrls;
}
