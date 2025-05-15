package com.ridemart.service;

import com.ridemart.dto.AdvertisementFilterDto;
import com.ridemart.dto.AdvertisementRequestDto;
import com.ridemart.dto.AdvertisementResponseDto;
import com.ridemart.dto.MotorbikeDetailsRequestDto;
import com.ridemart.dto.MotorbikeDetailsResponseDto;
import com.ridemart.entity.Advertisement;
import com.ridemart.entity.Make;
import com.ridemart.entity.Model;
import com.ridemart.entity.MotorbikeDetails;
import com.ridemart.entity.User;
import com.ridemart.mapper.AdvertisementMapper;
import com.ridemart.repository.AdvertisementRepository;
import com.ridemart.repository.MakeRepository;
import com.ridemart.repository.ModelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdvertisementServiceTest {

    @Mock
    private AdvertisementRepository advertisementRepository;

    @Mock
    private UserService userService;

    @Mock
    private MotorbikeDetailsService motorbikeDetailsService;

    @Mock
    private PhotoService photoService;

    @Mock
    private AdvertisementMapper advertisementMapper;

    @Mock
    private MakeRepository makeRepository;

    @Mock
    private ModelRepository modelRepository;

    @InjectMocks
    private AdvertisementService advertisementService;

    private Advertisement testAdvertisement;
    private AdvertisementRequestDto testRequestDto;
    private AdvertisementResponseDto testResponseDto;
    private User testUser;
    private MotorbikeDetails testMotorbikeDetails;
    private MotorbikeDetailsResponseDto testMotorbikeDetailsResponseDto;
    private Make testMake;
    private Model testModel;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1);
        testUser.setUsername("testuser");

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

        testMotorbikeDetailsResponseDto = new MotorbikeDetailsResponseDto();
        testMotorbikeDetailsResponseDto.setId(1);
        testMotorbikeDetailsResponseDto.setMake("HONDA");
        testMotorbikeDetailsResponseDto.setModel("CBR600RR");

        testAdvertisement = new Advertisement();
        testAdvertisement.setId(1);
        testAdvertisement.setUser(testUser);
        testAdvertisement.setMotorbikeDetails(testMotorbikeDetails);

        testRequestDto = new AdvertisementRequestDto();
        testRequestDto.setMotorbikeDetails(new MotorbikeDetailsRequestDto());
        testRequestDto.getMotorbikeDetails().setMake("HONDA");
        testRequestDto.getMotorbikeDetails().setModel("CBR600RR");

        testResponseDto = new AdvertisementResponseDto();
        testResponseDto.setId(1);
    }

    @Test
    void getAllAdvertisements_ShouldReturnListOfAdvertisements() {
        List<Advertisement> advertisements = Arrays.asList(testAdvertisement);
        List<AdvertisementResponseDto> expectedDtos = Arrays.asList(testResponseDto);
        when(advertisementRepository.findAll()).thenReturn(advertisements);
        when(advertisementMapper.toResponseDto(any(Advertisement.class))).thenReturn(testResponseDto);

        List<AdvertisementResponseDto> result = advertisementService.getAllAdvertisements();

        assertNotNull(result);
        assertEquals(expectedDtos.size(), result.size());
        verify(advertisementRepository).findAll();
        verify(advertisementMapper).toResponseDto(any(Advertisement.class));
    }

    @Test
    void createAdvertisement_ShouldReturnCreatedAdvertisement() {
        when(userService.getAuthenticatedUser()).thenReturn(testUser);
        when(motorbikeDetailsService.createMotorbikeDetails(any())).thenReturn(testMotorbikeDetails);
        when(advertisementMapper.toEntity(any())).thenReturn(testAdvertisement);
        when(advertisementRepository.save(any())).thenReturn(testAdvertisement);
        when(advertisementMapper.toResponseDto(any())).thenReturn(testResponseDto);

        AdvertisementResponseDto result = advertisementService.createAdvertisement(testRequestDto);

        assertNotNull(result);
        assertEquals(testResponseDto.getId(), result.getId());
        verify(userService).getAuthenticatedUser();
        verify(motorbikeDetailsService).createMotorbikeDetails(any());
        verify(advertisementMapper).toEntity(any());
        verify(advertisementRepository).save(any());
        verify(advertisementMapper).toResponseDto(any());
    }

    @Test
    void getAdvertisementById_ShouldReturnAdvertisement() {
        when(advertisementRepository.findById(1)).thenReturn(Optional.of(testAdvertisement));
        when(advertisementMapper.toResponseDto(testAdvertisement)).thenReturn(testResponseDto);

        AdvertisementResponseDto result = advertisementService.getAdvertisementById(1);

        assertNotNull(result);
        assertEquals(testResponseDto.getId(), result.getId());
        verify(advertisementRepository).findById(1);
        verify(advertisementMapper).toResponseDto(testAdvertisement);
    }

    @Test
    void getMyAdvertisements_ShouldReturnUserAdvertisements() {
        when(userService.getAuthenticatedUser()).thenReturn(testUser);
        when(advertisementRepository.findByUserId(1)).thenReturn(Arrays.asList(testAdvertisement));
        when(advertisementMapper.toResponseDto(any())).thenReturn(testResponseDto);

        List<AdvertisementResponseDto> result = advertisementService.getMyAdvertisements();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(userService).getAuthenticatedUser();
        verify(advertisementRepository).findByUserId(1);
        verify(advertisementMapper).toResponseDto(any());
    }

    @Test
    void filterAdvertisements_ShouldReturnFilteredAdvertisements() {
        AdvertisementFilterDto filterDto = new AdvertisementFilterDto();
        filterDto.setMake("HONDA");
        filterDto.setModel("CBR600RR");

        when(makeRepository.findByName("HONDA")).thenReturn(Optional.of(testMake));
        when(modelRepository.findByMakeId(1)).thenReturn(Arrays.asList(testModel));
        when(advertisementRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(testAdvertisement));
        when(advertisementMapper.toResponseDto(any())).thenReturn(testResponseDto);

        List<AdvertisementResponseDto> result = advertisementService.filterAdvertisements(filterDto);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(makeRepository).findByName("HONDA");
        verify(modelRepository).findByMakeId(1);
        verify(advertisementRepository).findAll(any(Specification.class));
        verify(advertisementMapper).toResponseDto(any());
    }

    @Test
    void updateAdvertisement_ShouldReturnUpdatedAdvertisement() {
        when(advertisementRepository.findById(1)).thenReturn(Optional.of(testAdvertisement));
        when(userService.getAuthenticatedUser()).thenReturn(testUser);
        when(motorbikeDetailsService.updateMotorbikeDetails(any(), any())).thenReturn(testMotorbikeDetailsResponseDto);
        when(advertisementRepository.save(any())).thenReturn(testAdvertisement);
        when(advertisementMapper.toResponseDto(any())).thenReturn(testResponseDto);

        AdvertisementResponseDto result = advertisementService.updateAdvertisement(1, testRequestDto);

        assertNotNull(result);
        assertEquals(testResponseDto.getId(), result.getId());
        verify(advertisementRepository).findById(1);
        verify(userService).getAuthenticatedUser();
        verify(motorbikeDetailsService).updateMotorbikeDetails(any(), any());
        verify(advertisementRepository).save(any());
        verify(advertisementMapper).toResponseDto(any());
    }

    @Test
    void deleteAdvertisement_ShouldDeleteAdvertisement() {
        when(advertisementRepository.findById(1)).thenReturn(Optional.of(testAdvertisement));
        when(userService.getAuthenticatedUser()).thenReturn(testUser);

        advertisementService.deleteAdvertisement(1);

        verify(advertisementRepository).findById(1);
        verify(userService).getAuthenticatedUser();
        verify(advertisementRepository).delete(testAdvertisement);
    }

    @Test
    void getAdvertisementById_ShouldThrowWhenNotFound() {
        when(advertisementRepository.findById(2)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> advertisementService.getAdvertisementById(2));
        verify(advertisementRepository).findById(2);
    }

    @Test
    void updateAdvertisement_ShouldThrowWhenNotFound() {
        when(advertisementRepository.findById(2)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> advertisementService.updateAdvertisement(2, testRequestDto));
        verify(advertisementRepository).findById(2);
    }

    @Test
    void deleteAdvertisement_ShouldThrowWhenNotFound() {
        when(advertisementRepository.findById(2)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> advertisementService.deleteAdvertisement(2));
        verify(advertisementRepository).findById(2);
    }

    @Test
    void getMyAdvertisements_ShouldReturnEmptyList() {
        when(userService.getAuthenticatedUser()).thenReturn(testUser);
        when(advertisementRepository.findByUserId(1)).thenReturn(Arrays.asList());
        List<AdvertisementResponseDto> result = advertisementService.getMyAdvertisements();
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userService).getAuthenticatedUser();
        verify(advertisementRepository).findByUserId(1);
    }

    @Test
    void filterAdvertisements_ShouldReturnEmptyList() {
        AdvertisementFilterDto filterDto = new AdvertisementFilterDto();
        filterDto.setMake("HONDA");
        filterDto.setModel("CBR600RR");
        when(makeRepository.findByName("HONDA")).thenReturn(Optional.of(testMake));
        when(modelRepository.findByMakeId(1)).thenReturn(Arrays.asList(testModel));
        when(advertisementRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList());
        List<AdvertisementResponseDto> result = advertisementService.filterAdvertisements(filterDto);
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(makeRepository).findByName("HONDA");
        verify(modelRepository).findByMakeId(1);
        verify(advertisementRepository).findAll(any(Specification.class));
    }

    @Test
    void createAdvertisement_ShouldThrowWhenUserNull() {
        when(userService.getAuthenticatedUser()).thenReturn(null);
        assertThrows(RuntimeException.class, () -> advertisementService.createAdvertisement(testRequestDto));
        verify(userService).getAuthenticatedUser();
    }

    @Test
    void updateAdvertisement_ShouldThrowWhenUserNull() {
        when(advertisementRepository.findById(1)).thenReturn(Optional.of(testAdvertisement));
        when(userService.getAuthenticatedUser()).thenReturn(null);
        assertThrows(RuntimeException.class, () -> advertisementService.updateAdvertisement(1, testRequestDto));
        verify(advertisementRepository).findById(1);
        verify(userService).getAuthenticatedUser();
    }
} 