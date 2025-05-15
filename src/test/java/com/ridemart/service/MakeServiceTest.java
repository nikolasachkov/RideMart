package com.ridemart.service;

import com.ridemart.entity.Make;
import com.ridemart.entity.Model;
import com.ridemart.repository.MakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MakeServiceTest {

    @Mock
    private MakeRepository makeRepository;

    @InjectMocks
    private MakeService makeService;

    private Make testMake;
    private Model testModel1;
    private Model testModel2;

    @BeforeEach
    void setUp() {
        testMake = new Make();
        testMake.setId(1);
        testMake.setName("HONDA");

        testModel1 = new Model();
        testModel1.setId(1);
        testModel1.setName("CBR600RR");
        testModel1.setMake(testMake);

        testModel2 = new Model();
        testModel2.setId(2);
        testModel2.setName("CBR1000RR");
        testModel2.setMake(testMake);

        testMake.setModels(Arrays.asList(testModel1, testModel2));
    }

    @Test
    void getAllMakeNames_ShouldReturnListOfMakeNames() {
        List<Make> makes = Arrays.asList(testMake);
        when(makeRepository.findAll()).thenReturn(makes);

        List<String> result = makeService.getAllMakeNames();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("HONDA", result.get(0));
        verify(makeRepository).findAll();
    }

    @Test
    void getModelNamesByMakeName_ShouldReturnListOfModelNames() {
        when(makeRepository.findByName("HONDA")).thenReturn(Optional.of(testMake));

        List<String> result = makeService.getModelNamesByMakeName("HONDA");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains("CBR600RR"));
        assertTrue(result.contains("CBR1000RR"));
        verify(makeRepository).findByName("HONDA");
    }

    @Test
    void getAllMakesAndModels_ShouldReturnMapOfMakesToModels() {
        List<Make> makes = Arrays.asList(testMake);
        when(makeRepository.findAll()).thenReturn(makes);

        Map<String, List<String>> result = makeService.getAllMakesAndModels();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.containsKey("HONDA"));
        assertEquals(2, result.get("HONDA").size());
        assertTrue(result.get("HONDA").contains("CBR600RR"));
        assertTrue(result.get("HONDA").contains("CBR1000RR"));
        verify(makeRepository).findAll();
    }
} 