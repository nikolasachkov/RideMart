package com.ridemart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementResponseDto {
    private Integer id;
    private String title;
    private String description;
    private String city;
    private String street;
    private String streetNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer userId;
    private MotorbikeDetailsResponseDto motorbikeDetails;
    private List<PhotoResponseDto> photos;
}
