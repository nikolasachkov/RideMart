package com.ridemart.service;

import com.ridemart.dto.AdvertisementResponseDto;
import com.ridemart.entity.Advertisement;
import com.ridemart.entity.SavedAdvertisement;
import com.ridemart.entity.User;
import com.ridemart.exception.UserNotFoundException;
import com.ridemart.exception.AdvertisementNotFoundException;
import com.ridemart.mapper.AdvertisementMapper;
import com.ridemart.repository.AdvertisementRepository;
import com.ridemart.repository.SavedAdvertisementRepository;
import com.ridemart.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class SavedAdvertisementService {

    private final SavedAdvertisementRepository savedAdvertisementRepository;
    private final UserRepository userRepository;
    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementMapper advertisementMapper;

    @Transactional
    public void saveAdvertisement(Integer userId, Integer advertisementId) {
        log.info("User {} is saving advertisement {}", userId, advertisementId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        Advertisement advertisement = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new AdvertisementNotFoundException(advertisementId));

        if (savedAdvertisementRepository.findByUserAndAdvertisement(user, advertisement).isPresent()) {
            return;
        }

        SavedAdvertisement savedAdvertisement = new SavedAdvertisement(null, user, advertisement);
        savedAdvertisementRepository.save(savedAdvertisement);
    }

    @Transactional
    public void unsaveAdvertisement(Integer userId, Integer advertisementId) {
        log.info("User {} is unsaving advertisement {}", userId, advertisementId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        Advertisement advertisement = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new AdvertisementNotFoundException(advertisementId));

        savedAdvertisementRepository.deleteByUserAndAdvertisement(user, advertisement);
    }

    public List<AdvertisementResponseDto> getSavedAdvertisements(Integer userId) {
        log.info("Fetching saved advertisements for user {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        List<SavedAdvertisement> savedAds = savedAdvertisementRepository.findByUser(user);

        List<Advertisement> advertisements = savedAds.stream()
                .map(SavedAdvertisement::getAdvertisement)
                .toList();

        return advertisementMapper.toResponseDtoList(advertisements);
    }
}
