package com.ridemart.service;

import com.ridemart.dto.AdvertisementFilterDto;
import com.ridemart.dto.AdvertisementRequestDto;
import com.ridemart.dto.AdvertisementResponseDto;
import com.ridemart.entity.Advertisement;
import com.ridemart.entity.MotorbikeDetails;
import com.ridemart.entity.User;
import com.ridemart.exception.AdvertisementNotFoundException;
import com.ridemart.exception.MissingMotorbikeDetailsException;
import com.ridemart.mapper.AdvertisementMapper;
import com.ridemart.repository.AdvertisementRepository;
import com.ridemart.repository.AdvertisementSpecifications;
import com.ridemart.repository.UserRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final UserService userService;
    private final MotorbikeDetailsService motorbikeDetailsService;
    private final PhotoService photoService;
    private final AdvertisementMapper advertisementMapper;

    public AdvertisementService(AdvertisementRepository advertisementRepository,
                                UserRepository userRepository, UserService userService,
                                MotorbikeDetailsService motorbikeDetailsService,
                                PhotoService photoService,
                                AdvertisementMapper advertisementMapper) {
        this.advertisementRepository = advertisementRepository;
        this.userService = userService;
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
        User user = userService.getAuthenticatedUser();

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

        advertisement.setTitle(dto.getTitle());
        advertisement.setDescription(dto.getDescription());
        advertisement.setCity(dto.getCity());
        advertisement.setStreet(dto.getStreet());
        advertisement.setStreetNumber(dto.getStreetNumber());

        motorbikeDetailsService.updateMotorbikeDetails(advertisement.getMotorbikeDetails(), dto.getMotorbikeDetails());

        if (dto.getPhotoUrls() != null && !dto.getPhotoUrls().isEmpty()) {
            photoService.updatePhotosForAdvertisement(id, dto.getPhotoUrls());
        }

        advertisement.setUpdatedAt(LocalDateTime.now());

        return advertisementMapper.toResponseDto(advertisementRepository.save(advertisement));
    }

    @Transactional
    public void deleteAdvertisement(Integer id) {
        Advertisement advertisement = getAdvertisementByIdOrThrow(id);
        validateAdvertisementOwnership(advertisement);

        advertisementRepository.delete(advertisement);
    }

    public List<AdvertisementResponseDto> filterAdvertisements(AdvertisementFilterDto filterDto) {
        Specification<Advertisement> specification = AdvertisementSpecifications.withFilters(filterDto);
        List<Advertisement> advertisements = advertisementRepository.findAll(specification);
        return advertisementMapper.toResponseDtoList(advertisements);
    }

    public AdvertisementResponseDto getAdvertisementById(Integer id) {
        return advertisementMapper.toResponseDto(getAdvertisementByIdOrThrow(id));
    }

    private Advertisement getAdvertisementByIdOrThrow(Integer id) {
        return advertisementRepository.findById(id)
                .orElseThrow(() -> new AdvertisementNotFoundException(id));
    }

    private void validateAdvertisementOwnership(Advertisement advertisement) {
        User user = userService.getAuthenticatedUser();
        if (!advertisement.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You can only modify your own advertisements.");
        }
    }

    public List<AdvertisementResponseDto> getMyAdvertisements() {
        User user = userService.getAuthenticatedUser();
        List<Advertisement> myAds = advertisementRepository.findByUserId(user.getId());
        return advertisementMapper.toResponseDtoList(myAds);
    }

}