package com.ridemart.service;

import com.ridemart.dto.MotorbikeDetailsRequestDto;
import com.ridemart.dto.MotorbikeDetailsResponseDto;
import com.ridemart.entity.MotorbikeDetails;
import com.ridemart.mapper.MotorbikeDetailsMapper;
import com.ridemart.repository.MotorbikeDetailsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MotorbikeDetailsService {

    private final MotorbikeDetailsRepository motorbikeDetailsRepository;
    private final MotorbikeDetailsMapper motorbikeDetailsMapper;

    public MotorbikeDetailsService(MotorbikeDetailsRepository motorbikeDetailsRepository,
                                   MotorbikeDetailsMapper motorbikeDetailsMapper) {
        this.motorbikeDetailsRepository = motorbikeDetailsRepository;
        this.motorbikeDetailsMapper = motorbikeDetailsMapper;
    }

    @Transactional
    public MotorbikeDetails createMotorbikeDetails(MotorbikeDetailsRequestDto dto) {
        return motorbikeDetailsRepository.save(motorbikeDetailsMapper.toEntity(dto));
    }

    @Transactional
    public MotorbikeDetailsResponseDto updateMotorbikeDetails(MotorbikeDetails existingDetails, MotorbikeDetailsRequestDto dto) {
        MotorbikeDetails updatedDetails = motorbikeDetailsMapper.toEntity(dto);
        updatedDetails.setId(existingDetails.getId());

        return motorbikeDetailsMapper.toResponseDto(motorbikeDetailsRepository.save(updatedDetails));
    }
}
