package com.ridemart.controller;

import com.ridemart.enums.Make;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/motorcycles")
public class MakeModelController {

    @GetMapping("/makes")
    public List<String> getMakes() {
        return Arrays.stream(Make.values())
                .map(Enum::name)
                .toList();
    }

    @GetMapping("/models/{make}")
    public List<String> getModelsByMake(@PathVariable String make) {
        return Make.valueOf(make.toUpperCase()).getModels();
    }

    @GetMapping("/all")
    public Map<String, List<String>> getAllMakesAndModels() {
        return Make.getAllMakesAndModels();
    }
}

