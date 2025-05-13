package com.ridemart.controller;

import com.ridemart.dto.AdvertisementFilterDto;
import com.ridemart.dto.AdvertisementRequestDto;
import com.ridemart.dto.AdvertisementResponseDto;
import com.ridemart.service.AdvertisementService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/advertisements")
@AllArgsConstructor
@Slf4j
public class AdvertisementController {

    private final AdvertisementService advertisementService;


    @GetMapping
    public ResponseEntity<List<AdvertisementResponseDto>> getAllAdvertisements() {
        log.info("Fetching all advertisements");
        return ResponseEntity.ok(advertisementService.getAllAdvertisements());
    }

    @GetMapping("/filter")
    public ResponseEntity<List<AdvertisementResponseDto>> filterAdvertisements(AdvertisementFilterDto filterDto) {
        log.info("Filtering advertisements with criteria: {}", filterDto);
        return ResponseEntity.ok(advertisementService.filterAdvertisements(filterDto));
    }

    @PostMapping
    public ResponseEntity<AdvertisementResponseDto> createAdvertisement(@RequestBody AdvertisementRequestDto dto) {
        log.info("Creating new advertisement");
        return ResponseEntity.ok(advertisementService.createAdvertisement(dto));
    }

    @GetMapping("/my-ads")
    public ResponseEntity<List<AdvertisementResponseDto>> getMyAdvertisements() {
        log.info("Fetching my advertisements");
        return ResponseEntity.ok(advertisementService.getMyAdvertisements());
    }


    @GetMapping("/{id}")
    public ResponseEntity<AdvertisementResponseDto> getAdvertisementById(@PathVariable Integer id) {
        log.info("Fetching advertisement with ID {}", id);
        return ResponseEntity.ok(advertisementService.getAdvertisementById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdvertisementResponseDto> updateAdvertisement(@PathVariable Integer id,
                                                                        @RequestBody AdvertisementRequestDto dto) {
        log.info("Updating advertisement with ID {}", id);
        return ResponseEntity.ok(advertisementService.updateAdvertisement(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdvertisement(@PathVariable Integer id) {
        log.info("Deleting advertisement with ID {}", id);
        advertisementService.deleteAdvertisement(id);
        return ResponseEntity.noContent().build();
    }
}
