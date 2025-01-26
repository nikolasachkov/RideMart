package com.ridemart.dto;

import lombok.Data;

@Data
public class UserResponseDto {
    private Integer id;
    private String username;
    private String email;
    private String phoneNumber;
}
