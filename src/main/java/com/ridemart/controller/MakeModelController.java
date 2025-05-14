package com.ridemart.controller;

import com.ridemart.service.MakeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/motorcycles")
@AllArgsConstructor
@Slf4j
public class MakeModelController {

    private final MakeService makeService;

    @GetMapping("/makes")
    public ResponseEntity<List<String>> getMakes() {
        log.info("GET /makes");
        return ResponseEntity.ok(makeService.getAllMakeNames());
    }

    @GetMapping("/models/{make}")
    public ResponseEntity<List<String>> getModelsByMake(@PathVariable String make) {
        log.info("GET /models/{}", make);
        List<String> models = makeService.getModelNamesByMakeName(make);
        if (models.isEmpty()) {
            log.warn("No models found for make {}", make);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(models);
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, List<String>>> getAllMakesAndModels() {
        log.info("GET /all");
        return ResponseEntity.ok(makeService.getAllMakesAndModels());
    }
}
