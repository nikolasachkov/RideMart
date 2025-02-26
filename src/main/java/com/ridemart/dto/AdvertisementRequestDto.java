package com.ridemart.dto;

import lombok.Data;
import java.util.List;

@Data
public class AdvertisementRequestDto {
    private String title;
    private String description;
    private String city;
    private String street;
    private String streetNumber;
    private MotorbikeDetailsRequestDto motorbikeDetails;
    private List<String> photoUrls;
}
