package com.ridemart.service;

import com.ridemart.dto.ModelDto;
import com.ridemart.mapper.ModelMapper;
import com.ridemart.repository.ModelRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ModelService {
    private final ModelRepository modelRepo;
    private final ModelMapper modelMapper;

    public List<ModelDto> getModelsByMake(Integer makeId) {
        log.info("Fetching models for make id {}", makeId);
        List<ModelDto> models = modelRepo.findByMakeId(makeId)
                .stream()
                .map(modelMapper::toDto)
                .toList();
        log.debug("Found {} models for make id {}", models.size(), makeId);
        return models;
    }

    public List<ModelDto> getAllModels() {
        log.info("Fetching all models");
        List<ModelDto> models = modelRepo.findAll()
                .stream()
                .map(modelMapper::toDto)
                .toList();
        log.debug("Found {} total models", models.size());
        return models;
    }
}
