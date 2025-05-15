package com.ridemart.controller;

import com.ridemart.dto.AdvertisementFilterDto;
import com.ridemart.dto.AdvertisementRequestDto;
import com.ridemart.dto.AdvertisementResponseDto;
import com.ridemart.service.AdvertisementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdvertisementControllerTest {

    @Mock
    private AdvertisementService advertisementService;

    @InjectMocks
    private AdvertisementController advertisementController;

    private AdvertisementRequestDto testRequestDto;
    private AdvertisementResponseDto testResponseDto;
    private AdvertisementFilterDto testFilterDto;

    @BeforeEach
    void setUp() {
        testRequestDto = new AdvertisementRequestDto();
        testRequestDto.setTitle("Test Advertisement");

        testResponseDto = new AdvertisementResponseDto();
        testResponseDto.setId(1);
        testResponseDto.setTitle("Test Advertisement");

        testFilterDto = new AdvertisementFilterDto();
        testFilterDto.setMake("HONDA");
        testFilterDto.setModel("CBR600RR");
    }

    @Test
    void getAllAdvertisements_ShouldReturnListOfAdvertisements() {
        List<AdvertisementResponseDto> expectedDtos = Arrays.asList(testResponseDto);
        when(advertisementService.getAllAdvertisements()).thenReturn(expectedDtos);

        ResponseEntity<List<AdvertisementResponseDto>> response = advertisementController.getAllAdvertisements();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedDtos, response.getBody());
        verify(advertisementService).getAllAdvertisements();
    }

    @Test
    void filterAdvertisements_ShouldReturnFilteredAdvertisements() {
        List<AdvertisementResponseDto> expectedDtos = Arrays.asList(testResponseDto);
        when(advertisementService.filterAdvertisements(any(AdvertisementFilterDto.class))).thenReturn(expectedDtos);

        ResponseEntity<List<AdvertisementResponseDto>> response = advertisementController.filterAdvertisements(testFilterDto);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedDtos, response.getBody());
        verify(advertisementService).filterAdvertisements(testFilterDto);
    }

    @Test
    void createAdvertisement_ShouldReturnCreatedAdvertisement() {
        when(advertisementService.createAdvertisement(any(AdvertisementRequestDto.class))).thenReturn(testResponseDto);

        ResponseEntity<AdvertisementResponseDto> response = advertisementController.createAdvertisement(testRequestDto);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(testResponseDto, response.getBody());
        verify(advertisementService).createAdvertisement(testRequestDto);
    }

    @Test
    void getMyAdvertisements_ShouldReturnUserAdvertisements() {
        List<AdvertisementResponseDto> expectedDtos = Arrays.asList(testResponseDto);
        when(advertisementService.getMyAdvertisements()).thenReturn(expectedDtos);

        ResponseEntity<List<AdvertisementResponseDto>> response = advertisementController.getMyAdvertisements();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedDtos, response.getBody());
        verify(advertisementService).getMyAdvertisements();
    }

    @Test
    void getAdvertisementById_ShouldReturnAdvertisement() {
        when(advertisementService.getAdvertisementById(1)).thenReturn(testResponseDto);

        ResponseEntity<AdvertisementResponseDto> response = advertisementController.getAdvertisementById(1);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(testResponseDto, response.getBody());
        verify(advertisementService).getAdvertisementById(1);
    }

    @Test
    void updateAdvertisement_ShouldReturnUpdatedAdvertisement() {
        when(advertisementService.updateAdvertisement(anyInt(), any(AdvertisementRequestDto.class)))
                .thenReturn(testResponseDto);

        ResponseEntity<AdvertisementResponseDto> response = advertisementController.updateAdvertisement(1, testRequestDto);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(testResponseDto, response.getBody());
        verify(advertisementService).updateAdvertisement(1, testRequestDto);
    }

    @Test
    void deleteAdvertisement_ShouldReturnNoContent() {
        ResponseEntity<Void> response = advertisementController.deleteAdvertisement(1);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(advertisementService).deleteAdvertisement(1);
    }
} 