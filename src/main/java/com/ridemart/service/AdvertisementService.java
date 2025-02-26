package com.ridemart.service;

import com.ridemart.dto.AdvertisementRequestDto;
import com.ridemart.dto.AdvertisementResponseDto;
import com.ridemart.entity.Advertisement;
import com.ridemart.entity.MotorbikeDetails;
import com.ridemart.entity.User;
import com.ridemart.exception.AdvertisementNotFoundException;
import com.ridemart.exception.MissingMotorbikeDetailsException;
import com.ridemart.exception.UserNotFoundException;
import com.ridemart.mapper.AdvertisementMapper;
import com.ridemart.repository.AdvertisementRepository;
import com.ridemart.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final UserRepository userRepository;
    private final MotorbikeDetailsService motorbikeDetailsService;
    private final PhotoService photoService;
    private final AdvertisementMapper advertisementMapper;

    public AdvertisementService(AdvertisementRepository advertisementRepository,
                                UserRepository userRepository,
                                MotorbikeDetailsService motorbikeDetailsService,
                                PhotoService photoService,
                                AdvertisementMapper advertisementMapper) {
        this.advertisementRepository = advertisementRepository;
        this.userRepository = userRepository;
        this.motorbikeDetailsService = motorbikeDetailsService;
        this.photoService = photoService;
        this.advertisementMapper = advertisementMapper;
    }

    public List<AdvertisementResponseDto> getAllAdvertisements() {
        return advertisementRepository.findAll().stream()
                .map(advertisementMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public AdvertisementResponseDto createAdvertisement(AdvertisementRequestDto dto) {
        User user = getAuthenticatedUser();

        if (dto.getMotorbikeDetails() == null) {
            throw new MissingMotorbikeDetailsException();
        }

        MotorbikeDetails motorbikeDetails = motorbikeDetailsService.createMotorbikeDetails(dto.getMotorbikeDetails());

        Advertisement advertisement = advertisementMapper.toEntity(dto);
        advertisement.setUser(user);
        advertisement.setMotorbikeDetails(motorbikeDetails);

        Advertisement savedAdvertisement = advertisementRepository.save(advertisement);

        if (dto.getPhotoUrls() != null && !dto.getPhotoUrls().isEmpty()) {
            photoService.addPhotosToAdvertisement(savedAdvertisement.getId(), dto.getPhotoUrls());
        }

        return advertisementMapper.toResponseDto(savedAdvertisement);
    }


    @Transactional
    public AdvertisementResponseDto updateAdvertisement(Integer id, AdvertisementRequestDto dto) {
        Advertisement advertisement = getAdvertisementByIdOrThrow(id);
        validateAdvertisementOwnership(advertisement);

        if (dto.getTitle() != null) {
            advertisement.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null) {
            advertisement.setDescription(dto.getDescription());
        }
        if (dto.getCity() != null) {
            advertisement.setCity(dto.getCity());
        }
        if (dto.getStreet() != null) {
            advertisement.setStreet(dto.getStreet());
        }
        if (dto.getStreetNumber() != null) {
            advertisement.setStreetNumber(dto.getStreetNumber());
        }

        if (dto.getMotorbikeDetails() != null) {
            motorbikeDetailsService.updateMotorbikeDetails(advertisement.getMotorbikeDetails(), dto.getMotorbikeDetails());
        }

        photoService.updatePhotosForAdvertisement(id, dto.getPhotoUrls());

        return advertisementMapper.toResponseDto(advertisementRepository.save(advertisement));
    }

    @Transactional
    public void deleteAdvertisement(Integer id) {
        Advertisement advertisement = getAdvertisementByIdOrThrow(id);
        validateAdvertisementOwnership(advertisement);

        advertisementRepository.delete(advertisement);
    }

    public AdvertisementResponseDto getAdvertisementById(Integer id) {
        return advertisementMapper.toResponseDto(getAdvertisementByIdOrThrow(id));
    }

    private Advertisement getAdvertisementByIdOrThrow(Integer id) {
        return advertisementRepository.findById(id)
                .orElseThrow(() -> new AdvertisementNotFoundException(id));
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException(authentication.getName()));
    }

    private void validateAdvertisementOwnership(Advertisement advertisement) {
        User user = getAuthenticatedUser();
        if (!advertisement.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You can only modify your own advertisements.");
        }
    }
}