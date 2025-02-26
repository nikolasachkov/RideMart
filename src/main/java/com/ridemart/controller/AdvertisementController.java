package com.ridemart.controller;

import com.ridemart.dto.AdvertisementRequestDto;
import com.ridemart.dto.AdvertisementResponseDto;
import com.ridemart.service.AdvertisementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/advertisements")
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    public AdvertisementController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @GetMapping
    public List<AdvertisementResponseDto> getAllAdvertisements() {
        return advertisementService.getAllAdvertisements();
    }

    @PostMapping
    public ResponseEntity<AdvertisementResponseDto> createAdvertisement(@RequestBody AdvertisementRequestDto dto) {
        return ResponseEntity.ok(advertisementService.createAdvertisement(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvertisementResponseDto> getAdvertisementById(@PathVariable Integer id) {
        return ResponseEntity.ok(advertisementService.getAdvertisementById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdvertisementResponseDto> updateAdvertisement(@PathVariable Integer id,
                                                                        @RequestBody AdvertisementRequestDto dto) {
        return ResponseEntity.ok(advertisementService.updateAdvertisement(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdvertisement(@PathVariable Integer id) {
        advertisementService.deleteAdvertisement(id);
        return ResponseEntity.noContent().build();
    }
}
