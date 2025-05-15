package com.ridemart.service;

import com.ridemart.dto.MotorbikeDetailsRequestDto;
import com.ridemart.dto.MotorbikeDetailsResponseDto;
import com.ridemart.entity.Model;
import com.ridemart.entity.MotorbikeDetails;
import com.ridemart.mapper.MotorbikeDetailsMapper;
import com.ridemart.repository.ModelRepository;
import com.ridemart.repository.MotorbikeDetailsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class MotorbikeDetailsService {

    private final MotorbikeDetailsRepository motorbikeDetailsRepository;
    private final ModelRepository              modelRepository;
    private final MotorbikeDetailsMapper       motorbikeDetailsMapper;

    @Transactional
    public MotorbikeDetails createMotorbikeDetails(MotorbikeDetailsRequestDto dto) {
        log.info("Creating MotorbikeDetails for make={} model={}", dto.getMake(), dto.getModel());
        Model model = modelRepository.findByName(dto.getModel().toUpperCase())
                .orElseThrow(() -> new EntityNotFoundException("Model not found: " + dto.getModel()));

        if (!model.getMake().getName().equalsIgnoreCase(dto.getMake())) {
            throw new IllegalArgumentException(
                    "Model " + dto.getModel() + " does not belong to make " + dto.getMake());
        }

        MotorbikeDetails details = motorbikeDetailsMapper.toEntity(dto);
        details.setModel(model);
        return motorbikeDetailsRepository.save(details);
    }

    @Transactional
    public MotorbikeDetailsResponseDto updateMotorbikeDetails(
            MotorbikeDetails existingDetails,
            MotorbikeDetailsRequestDto dto) {
        log.info("Updating MotorbikeDetails id={} to make={} model={}",
                existingDetails.getId(), dto.getMake(), dto.getModel());

        Model model = modelRepository.findByName(dto.getModel().toUpperCase())
                .orElseThrow(() -> new EntityNotFoundException("Model not found: " + dto.getModel()));
        if (!model.getMake().getName().equalsIgnoreCase(dto.getMake())) {
            throw new IllegalArgumentException(
                    "Model " + dto.getModel() + " does not belong to make " + dto.getMake());
        }

        MotorbikeDetails updated = motorbikeDetailsMapper.toEntity(dto);
        updated.setId(existingDetails.getId());
        updated.setModel(model);
        MotorbikeDetails saved = motorbikeDetailsRepository.save(updated);
        return motorbikeDetailsMapper.toResponseDto(saved);
    }
}
