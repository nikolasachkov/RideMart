package com.ridemart.controller;

import com.ridemart.service.MakeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MakeModelControllerTest {

    @Mock
    private MakeService makeService;

    @InjectMocks
    private MakeModelController makeModelController;

    private List<String> testMakeNames;
    private List<String> testModelNames;
    private Map<String, List<String>> testMakeModelMap;

    @BeforeEach
    void setUp() {
        testMakeNames = Arrays.asList("HONDA", "YAMAHA", "KAWASAKI");
        testModelNames = Arrays.asList("CBR600RR", "CBR1000RR");
        testMakeModelMap = Map.of(
                "HONDA", Arrays.asList("CBR600RR", "CBR1000RR"),
                "YAMAHA", Arrays.asList("R6", "R1"),
                "KAWASAKI", Arrays.asList("ZX-6R", "ZX-10R")
        );
    }

    @Test
    void getMakes_ShouldReturnListOfMakes() {
        when(makeService.getAllMakeNames()).thenReturn(testMakeNames);

        ResponseEntity<List<String>> response = makeModelController.getMakes();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(testMakeNames, response.getBody());
        verify(makeService).getAllMakeNames();
    }

    @Test
    void getModelsByMake_ShouldReturnListOfModels() {
        when(makeService.getModelNamesByMakeName("HONDA")).thenReturn(testModelNames);

        ResponseEntity<List<String>> response = makeModelController.getModelsByMake("HONDA");

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(testModelNames, response.getBody());
        verify(makeService).getModelNamesByMakeName("HONDA");
    }

    @Test
    void getAllMakesAndModels_ShouldReturnMapOfMakesToModels() {
        when(makeService.getAllMakesAndModels()).thenReturn(testMakeModelMap);

        ResponseEntity<Map<String, List<String>>> response = makeModelController.getAllMakesAndModels();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(testMakeModelMap, response.getBody());
        verify(makeService).getAllMakesAndModels();
    }
} 