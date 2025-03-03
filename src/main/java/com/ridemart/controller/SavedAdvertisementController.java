package com.ridemart.controller;

import com.ridemart.dto.AdvertisementResponseDto;
import com.ridemart.service.UserService;
import com.ridemart.service.SavedAdvertisementService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saved-ads")
public class SavedAdvertisementController {

    private final SavedAdvertisementService savedAdvertisementService;
    private final UserService userService;

    public SavedAdvertisementController(SavedAdvertisementService savedAdvertisementService, UserService userService) {
        this.savedAdvertisementService = savedAdvertisementService;
        this.userService = userService;
    }

    @PostMapping("/{advertisementId}")
    public ResponseEntity<String> saveAdvertisement(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Integer advertisementId) {

        Integer userId = userService.getUserIdFromUsername(userDetails.getUsername());
        savedAdvertisementService.saveAdvertisement(userId, advertisementId);
        return ResponseEntity.ok("Advertisement saved successfully!");
    }

    @DeleteMapping("/{advertisementId}")
    public ResponseEntity<String> unsaveAdvertisement(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Integer advertisementId) {

        Integer userId = userService.getUserIdFromUsername(userDetails.getUsername());
        savedAdvertisementService.unsaveAdvertisement(userId, advertisementId);
        return ResponseEntity.ok("Advertisement unsaved successfully!");
    }

    @GetMapping
    public ResponseEntity<List<AdvertisementResponseDto>> getSavedAdvertisements(
            @AuthenticationPrincipal UserDetails userDetails) {

        Integer userId = userService.getUserIdFromUsername(userDetails.getUsername());
        return ResponseEntity.ok(savedAdvertisementService.getSavedAdvertisements(userId));
    }
}
