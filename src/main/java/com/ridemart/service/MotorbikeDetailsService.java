package com.ridemart.service;

import com.ridemart.dto.MotorbikeDetailsRequestDto;
import com.ridemart.dto.MotorbikeDetailsResponseDto;
import com.ridemart.entity.MotorbikeDetails;
import com.ridemart.mapper.MotorbikeDetailsMapper;
import com.ridemart.repository.MotorbikeDetailsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class MotorbikeDetailsService {

    private final MotorbikeDetailsRepository motorbikeDetailsRepository;
    private final MotorbikeDetailsMapper motorbikeDetailsMapper;

    @Transactional
    public MotorbikeDetails createMotorbikeDetails(MotorbikeDetailsRequestDto dto) {
        log.info("Creating motorbike details");
        return motorbikeDetailsRepository.save(motorbikeDetailsMapper.toEntity(dto));
    }

    @Transactional
    public MotorbikeDetailsResponseDto updateMotorbikeDetails(MotorbikeDetails existingDetails, MotorbikeDetailsRequestDto dto) {
        log.info("Updating motorbike details with ID {}", existingDetails.getId());
        MotorbikeDetails updatedDetails = motorbikeDetailsMapper.toEntity(dto);
        updatedDetails.setId(existingDetails.getId());

        return motorbikeDetailsMapper.toResponseDto(motorbikeDetailsRepository.save(updatedDetails));
    }
}
