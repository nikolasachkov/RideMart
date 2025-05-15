package com.ridemart.service;

import com.ridemart.entity.Make;
import com.ridemart.repository.MakeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class MakeService {

    private final MakeRepository makeRepository;

    public List<String> getAllMakeNames() {
        log.info("Fetching all make names");
        List<String> names = makeRepository.findAll()
                .stream()
                .map(Make::getName)
                .toList();
        log.debug("Found {} makes", names.size());
        return names;
    }

    public List<String> getModelNamesByMakeName(String makeName) {
        log.info("Fetching models for make={}", makeName);
        List<String> models = makeRepository.findByName(makeName.toUpperCase())
                .map(mk -> mk.getModels().stream()
                        .map(m -> m.getName())
                        .toList())
                .orElse(List.of());
        log.debug("Found {} models for make={}", models.size(), makeName);
        return models;
    }

    public Map<String, List<String>> getAllMakesAndModels() {
        log.info("Building map of all makes to their model names");
        Map<String,List<String>> map = makeRepository.findAll()
                .stream()
                .collect(Collectors.toMap(
                        Make::getName,
                        mk -> mk.getModels().stream()
                                .map(m -> m.getName())
                                .toList()
                ));
        log.debug("Built map with {} entries", map.size());
        return map;
    }
}
