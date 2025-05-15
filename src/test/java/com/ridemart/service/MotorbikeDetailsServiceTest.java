package com.ridemart.service;

import com.ridemart.dto.MotorbikeDetailsRequestDto;
import com.ridemart.dto.MotorbikeDetailsResponseDto;
import com.ridemart.entity.Make;
import com.ridemart.entity.Model;
import com.ridemart.entity.MotorbikeDetails;
import com.ridemart.mapper.MotorbikeDetailsMapper;
import com.ridemart.repository.ModelRepository;
import com.ridemart.repository.MotorbikeDetailsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MotorbikeDetailsServiceTest {

    @Mock
    private MotorbikeDetailsRepository motorbikeDetailsRepository;

    @Mock
    private ModelRepository modelRepository;

    @Mock
    private MotorbikeDetailsMapper motorbikeDetailsMapper;

    @InjectMocks
    private MotorbikeDetailsService motorbikeDetailsService;

    private Make testMake;
    private Model testModel;
    private MotorbikeDetails testMotorbikeDetails;
    private MotorbikeDetailsRequestDto testRequestDto;
    private MotorbikeDetailsResponseDto testResponseDto;

    @BeforeEach
    void setUp() {
        testMake = new Make();
        testMake.setId(1);
        testMake.setName("HONDA");

        testModel = new Model();
        testModel.setId(1);
        testModel.setName("CBR600RR");
        testModel.setMake(testMake);

        testMotorbikeDetails = new MotorbikeDetails();
        testMotorbikeDetails.setId(1);
        testMotorbikeDetails.setModel(testModel);

        testRequestDto = new MotorbikeDetailsRequestDto();
        testRequestDto.setMake("HONDA");
        testRequestDto.setModel("CBR600RR");

        testResponseDto = new MotorbikeDetailsResponseDto();
        testResponseDto.setId(1);
        testResponseDto.setMake("HONDA");
        testResponseDto.setModel("CBR600RR");
    }

    @Test
    void createMotorbikeDetails_ShouldReturnCreatedDetails() {
        when(modelRepository.findByName("CBR600RR")).thenReturn(Optional.of(testModel));
        when(motorbikeDetailsMapper.toEntity(testRequestDto)).thenReturn(testMotorbikeDetails);
        when(motorbikeDetailsRepository.save(any(MotorbikeDetails.class))).thenReturn(testMotorbikeDetails);

        MotorbikeDetails result = motorbikeDetailsService.createMotorbikeDetails(testRequestDto);

        assertNotNull(result);
        assertEquals(testMotorbikeDetails.getId(), result.getId());
        verify(modelRepository).findByName("CBR600RR");
        verify(motorbikeDetailsMapper).toEntity(testRequestDto);
        verify(motorbikeDetailsRepository).save(any(MotorbikeDetails.class));
    }

    @Test
    void updateMotorbikeDetails_ShouldReturnUpdatedDetails() {
        when(modelRepository.findByName("CBR600RR")).thenReturn(Optional.of(testModel));
        when(motorbikeDetailsMapper.toEntity(testRequestDto)).thenReturn(testMotorbikeDetails);
        when(motorbikeDetailsRepository.save(any(MotorbikeDetails.class))).thenReturn(testMotorbikeDetails);
        when(motorbikeDetailsMapper.toResponseDto(any(MotorbikeDetails.class))).thenReturn(testResponseDto);

        MotorbikeDetailsResponseDto result = motorbikeDetailsService.updateMotorbikeDetails(
                testMotorbikeDetails, testRequestDto);

        assertNotNull(result);
        assertEquals(testResponseDto.getId(), result.getId());
        verify(modelRepository).findByName("CBR600RR");
        verify(motorbikeDetailsMapper).toEntity(testRequestDto);
        verify(motorbikeDetailsRepository).save(any(MotorbikeDetails.class));
        verify(motorbikeDetailsMapper).toResponseDto(any(MotorbikeDetails.class));
    }
} 