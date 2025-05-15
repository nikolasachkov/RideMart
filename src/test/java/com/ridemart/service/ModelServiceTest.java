package com.ridemart.service;

import com.ridemart.dto.ModelDto;
import com.ridemart.entity.Make;
import com.ridemart.entity.Model;
import com.ridemart.mapper.ModelMapper;
import com.ridemart.repository.ModelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModelServiceTest {

    @Mock
    private ModelRepository modelRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ModelService modelService;

    private Model testModel1;
    private Model testModel2;
    private ModelDto testModelDto1;
    private ModelDto testModelDto2;
    private Make testMake;

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

        testModelDto1 = new ModelDto();
        testModelDto1.setId(1);
        testModelDto1.setName("CBR600RR");
        testModelDto1.setMakeId(1);

        testModelDto2 = new ModelDto();
        testModelDto2.setId(2);
        testModelDto2.setName("CBR1000RR");
        testModelDto2.setMakeId(1);
    }

    @Test
    void getModelsByMake_ShouldReturnListOfModels() {
        List<Model> models = Arrays.asList(testModel1, testModel2);
        List<ModelDto> expectedDtos = Arrays.asList(testModelDto1, testModelDto2);
        when(modelRepository.findByMakeId(1)).thenReturn(models);
        when(modelMapper.toDto(any(Model.class))).thenReturn(testModelDto1, testModelDto2);

        List<ModelDto> result = modelService.getModelsByMake(1);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(modelRepository).findByMakeId(1);
        verify(modelMapper, times(2)).toDto(any(Model.class));
    }

    @Test
    void getAllModels_ShouldReturnListOfAllModels() {
        List<Model> models = Arrays.asList(testModel1, testModel2);
        List<ModelDto> expectedDtos = Arrays.asList(testModelDto1, testModelDto2);
        when(modelRepository.findAll()).thenReturn(models);
        when(modelMapper.toDto(any(Model.class))).thenReturn(testModelDto1, testModelDto2);

        List<ModelDto> result = modelService.getAllModels();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(modelRepository).findAll();
        verify(modelMapper, times(2)).toDto(any(Model.class));
    }
} 