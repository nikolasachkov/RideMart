package com.ridemart.service;

import com.ridemart.dto.AdvertisementResponseDto;
import com.ridemart.entity.Advertisement;
import com.ridemart.entity.SavedAdvertisement;
import com.ridemart.entity.User;
import com.ridemart.mapper.AdvertisementMapper;
import com.ridemart.repository.AdvertisementRepository;
import com.ridemart.repository.SavedAdvertisementRepository;
import com.ridemart.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SavedAdvertisementServiceTest {

    @Mock
    private SavedAdvertisementRepository savedAdvertisementRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AdvertisementRepository advertisementRepository;

    @Mock
    private AdvertisementMapper advertisementMapper;

    @InjectMocks
    private SavedAdvertisementService savedAdvertisementService;

    private User testUser;
    private Advertisement testAdvertisement;
    private SavedAdvertisement testSavedAdvertisement;
    private AdvertisementResponseDto testResponseDto;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1);
        testUser.setUsername("testuser");

        testAdvertisement = new Advertisement();
        testAdvertisement.setId(1);

        testSavedAdvertisement = new SavedAdvertisement();
        testSavedAdvertisement.setId(1);
        testSavedAdvertisement.setUser(testUser);
        testSavedAdvertisement.setAdvertisement(testAdvertisement);

        testResponseDto = new AdvertisementResponseDto();
        testResponseDto.setId(1);
    }

    @Test
    void saveAdvertisement_ShouldSaveNewAdvertisement() {
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        when(advertisementRepository.findById(1)).thenReturn(Optional.of(testAdvertisement));
        when(savedAdvertisementRepository.findByUserAndAdvertisement(testUser, testAdvertisement))
                .thenReturn(Optional.empty());
        when(savedAdvertisementRepository.save(any(SavedAdvertisement.class)))
                .thenReturn(testSavedAdvertisement);

        savedAdvertisementService.saveAdvertisement(1, 1);

        verify(userRepository).findById(1);
        verify(advertisementRepository).findById(1);
        verify(savedAdvertisementRepository).findByUserAndAdvertisement(testUser, testAdvertisement);
        verify(savedAdvertisementRepository).save(any(SavedAdvertisement.class));
    }

    @Test
    void unsaveAdvertisement_ShouldRemoveSavedAdvertisement() {
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        when(advertisementRepository.findById(1)).thenReturn(Optional.of(testAdvertisement));

        savedAdvertisementService.unsaveAdvertisement(1, 1);

        verify(userRepository).findById(1);
        verify(advertisementRepository).findById(1);
        verify(savedAdvertisementRepository).deleteByUserAndAdvertisement(testUser, testAdvertisement);
    }

    @Test
    void getSavedAdvertisements_ShouldReturnListOfSavedAdvertisements() {
        List<SavedAdvertisement> savedAdvertisements = Arrays.asList(testSavedAdvertisement);
        List<Advertisement> advertisements = Arrays.asList(testAdvertisement);
        List<AdvertisementResponseDto> expectedDtos = Arrays.asList(testResponseDto);

        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        when(savedAdvertisementRepository.findByUser(testUser)).thenReturn(savedAdvertisements);
        when(advertisementMapper.toResponseDtoList(advertisements)).thenReturn(expectedDtos);

        List<AdvertisementResponseDto> result = savedAdvertisementService.getSavedAdvertisements(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userRepository).findById(1);
        verify(savedAdvertisementRepository).findByUser(testUser);
        verify(advertisementMapper).toResponseDtoList(advertisements);
    }
} 