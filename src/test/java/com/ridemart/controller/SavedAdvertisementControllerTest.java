package com.ridemart.controller;

import com.ridemart.dto.AdvertisementResponseDto;
import com.ridemart.service.SavedAdvertisementService;
import com.ridemart.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SavedAdvertisementControllerTest {

    @Mock
    private SavedAdvertisementService savedAdvertisementService;

    @Mock
    private UserService userService;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private SavedAdvertisementController savedAdvertisementController;

    private AdvertisementResponseDto testResponseDto;
    private List<AdvertisementResponseDto> testResponseDtos;

    @BeforeEach
    void setUp() {
        testResponseDto = new AdvertisementResponseDto();
        testResponseDto.setId(1);
        testResponseDto.setTitle("Test Advertisement");

        testResponseDtos = Arrays.asList(testResponseDto);
    }

    @Test
    void saveAdvertisement_ShouldReturnSuccessMessage() {
        when(userDetails.getUsername()).thenReturn("testuser");
        when(userService.getUserIdFromUsername("testuser")).thenReturn(1);

        ResponseEntity<String> response = savedAdvertisementController.saveAdvertisement(userDetails, 1);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Advertisement saved successfully!", response.getBody());
        verify(userService).getUserIdFromUsername("testuser");
        verify(savedAdvertisementService).saveAdvertisement(1, 1);
    }

    @Test
    void unsaveAdvertisement_ShouldReturnSuccessMessage() {
        when(userDetails.getUsername()).thenReturn("testuser");
        when(userService.getUserIdFromUsername("testuser")).thenReturn(1);

        ResponseEntity<String> response = savedAdvertisementController.unsaveAdvertisement(userDetails, 1);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Advertisement unsaved successfully!", response.getBody());
        verify(userService).getUserIdFromUsername("testuser");
        verify(savedAdvertisementService).unsaveAdvertisement(1, 1);
    }

    @Test
    void getSavedAdvertisements_ShouldReturnListOfSavedAdvertisements() {
        when(userDetails.getUsername()).thenReturn("testuser");
        when(userService.getUserIdFromUsername("testuser")).thenReturn(1);
        when(savedAdvertisementService.getSavedAdvertisements(1)).thenReturn(testResponseDtos);

        ResponseEntity<List<AdvertisementResponseDto>> response = savedAdvertisementController.getSavedAdvertisements(userDetails);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(testResponseDtos, response.getBody());
        verify(userService).getUserIdFromUsername("testuser");
        verify(savedAdvertisementService).getSavedAdvertisements(1);
    }
} 