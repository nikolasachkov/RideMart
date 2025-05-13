package com.ridemart.controller;

import com.ridemart.enums.Make;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/motorcycles")
@AllArgsConstructor
@Slf4j
public class MakeModelController {

    @GetMapping("/makes")
    public ResponseEntity<List<String>> getMakes() {
        log.info("Fetching all available makes");
        List<String> makes = Arrays.stream(Make.values())
                .map(Enum::name)
                .toList();
        return ResponseEntity.ok(makes);
    }

    @GetMapping("/models/{make}")
    public ResponseEntity<List<String>> getModelsByMake(@PathVariable String make) {
        log.info("Fetching models for make: {}", make);
        List<String> models = Make.valueOf(make.toUpperCase()).getModels();
        return ResponseEntity.ok(models);
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, List<String>>> getAllMakesAndModels() {
        log.info("Fetching all makes and their models");
        return ResponseEntity.ok(Make.getAllMakesAndModels());
    }
}
