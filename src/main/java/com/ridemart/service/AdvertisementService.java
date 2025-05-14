package com.ridemart.service;

import com.ridemart.dto.AdvertisementFilterDto;
import com.ridemart.dto.AdvertisementRequestDto;
import com.ridemart.dto.AdvertisementResponseDto;
import com.ridemart.entity.Advertisement;
import com.ridemart.entity.Model;
import com.ridemart.entity.MotorbikeDetails;
import com.ridemart.exception.AdvertisementNotFoundException;
import com.ridemart.exception.MissingMotorbikeDetailsException;
import com.ridemart.mapper.AdvertisementMapper;
import com.ridemart.repository.AdvertisementRepository;
import com.ridemart.repository.AdvertisementSpecifications;
import com.ridemart.repository.MakeRepository;
import com.ridemart.repository.ModelRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final UserService             userService;
    private final MotorbikeDetailsService motorbikeDetailsService;
    private final PhotoService            photoService;
    private final AdvertisementMapper     advertisementMapper;
    private final MakeRepository makeRepository;
    private final ModelRepository modelRepository;

    public List<AdvertisementResponseDto> getAllAdvertisements() {
        log.info("Fetching all advertisements");
        return advertisementRepository.findAll().stream()
                .map(advertisementMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public AdvertisementResponseDto createAdvertisement(AdvertisementRequestDto dto) {
        log.info("Creating new advertisement for user");
        var user = userService.getAuthenticatedUser();

        if (dto.getMotorbikeDetails() == null) {
            throw new MissingMotorbikeDetailsException();
        }

        MotorbikeDetails details = motorbikeDetailsService.createMotorbikeDetails(dto.getMotorbikeDetails());
        var ad = advertisementMapper.toEntity(dto);
        ad.setUser(user);
        ad.setMotorbikeDetails(details);

        var saved = advertisementRepository.save(ad);
        if (dto.getPhotoUrls() != null && !dto.getPhotoUrls().isEmpty()) {
            photoService.addPhotosToAdvertisement(saved.getId(), dto.getPhotoUrls());
        }

        return advertisementMapper.toResponseDto(saved);
    }

    @Transactional
    public AdvertisementResponseDto updateAdvertisement(Integer id, AdvertisementRequestDto dto) {
        log.info("Updating advertisement id={}", id);
        Advertisement ad = getAdvertisementByIdOrThrow(id);
        validateAdvertisementOwnership(ad);

        motorbikeDetailsService.updateMotorbikeDetails(ad.getMotorbikeDetails(), dto.getMotorbikeDetails());
        if (dto.getPhotoUrls() != null && !dto.getPhotoUrls().isEmpty()) {
            photoService.updatePhotosForAdvertisement(id, dto.getPhotoUrls());
        }
        ad.setUpdatedAt(LocalDateTime.now());

        Advertisement updated = advertisementRepository.save(ad);
        return advertisementMapper.toResponseDto(updated);
    }

    @Transactional
    public void deleteAdvertisement(Integer id) {
        log.info("Deleting advertisement id={}", id);
        Advertisement ad = getAdvertisementByIdOrThrow(id);
        validateAdvertisementOwnership(ad);
        advertisementRepository.delete(ad);
    }

    public List<AdvertisementResponseDto> filterAdvertisements(AdvertisementFilterDto filterDto) {
        log.info("Filtering advertisements by make='{}', model='{}'",
                filterDto.getMake(), filterDto.getModel());

        if (filterDto.getMake() != null && filterDto.getModel() != null) {
            makeRepository.findByName(filterDto.getMake().toUpperCase()).ifPresent(make -> {
                List<String> validModels = modelRepository
                        .findByMakeId(make.getId())
                        .stream()
                        .map(Model::getName)
                        .toList();
                if (!validModels.contains(filterDto.getModel())) {
                    log.info("Clearing stale model '{}' for make '{}'",
                            filterDto.getModel(), filterDto.getMake());
                    filterDto.setModel(null);
                }
            });
        }

        Specification<Advertisement> spec = AdvertisementSpecifications.withFilters(filterDto);
        return advertisementRepository
                .findAll(spec)
                .stream()
                .map(advertisementMapper::toResponseDto)
                .toList();
    }

    public AdvertisementResponseDto getAdvertisementById(Integer id) {
        log.info("Fetching advertisement id={}", id);
        return advertisementMapper.toResponseDto(getAdvertisementByIdOrThrow(id));
    }

    private Advertisement getAdvertisementByIdOrThrow(Integer id) {
        return advertisementRepository.findById(id)
                .orElseThrow(() -> new AdvertisementNotFoundException(id));
    }

    private void validateAdvertisementOwnership(Advertisement ad) {
        var user = userService.getAuthenticatedUser();
        if (!ad.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You can only modify your own advertisements.");
        }
    }

    public List<AdvertisementResponseDto> getMyAdvertisements() {
        log.info("Fetching my advertisements");
        return advertisementRepository.findByUserId(
                        userService.getAuthenticatedUser().getId()
                ).stream()
                .map(advertisementMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
