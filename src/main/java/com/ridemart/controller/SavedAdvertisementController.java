package com.ridemart.controller;

import com.ridemart.dto.AdvertisementResponseDto;
import com.ridemart.service.UserService;
import com.ridemart.service.SavedAdvertisementService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saved-ads")
@AllArgsConstructor
@Slf4j
public class SavedAdvertisementController {

    private final SavedAdvertisementService savedAdvertisementService;
    private final UserService userService;

    @PostMapping("/{advertisementId}")
    public ResponseEntity<String> saveAdvertisement(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Integer advertisementId) {

        Integer userId = userService.getUserIdFromUsername(userDetails.getUsername());
        log.info("User {} is saving advertisement {}", userId, advertisementId);
        savedAdvertisementService.saveAdvertisement(userId, advertisementId);
        return ResponseEntity.ok("Advertisement saved successfully!");
    }

    @DeleteMapping("/{advertisementId}")
    public ResponseEntity<String> unsaveAdvertisement(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Integer advertisementId) {

        Integer userId = userService.getUserIdFromUsername(userDetails.getUsername());
        log.info("User {} is unsaving advertisement {}", userId, advertisementId);
        savedAdvertisementService.unsaveAdvertisement(userId, advertisementId);
        return ResponseEntity.ok("Advertisement unsaved successfully!");
    }

    @GetMapping
    public ResponseEntity<List<AdvertisementResponseDto>> getSavedAdvertisements(
            @AuthenticationPrincipal UserDetails userDetails) {

        Integer userId = userService.getUserIdFromUsername(userDetails.getUsername());
        log.info("Fetching saved advertisements for user {}", userId);
        return ResponseEntity.ok(savedAdvertisementService.getSavedAdvertisements(userId));
    }
}
