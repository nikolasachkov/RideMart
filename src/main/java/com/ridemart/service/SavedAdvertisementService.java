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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SavedAdvertisementService {

    private final SavedAdvertisementRepository savedAdvertisementRepository;
    private final UserRepository userRepository;
    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementMapper advertisementMapper;

    public SavedAdvertisementService(SavedAdvertisementRepository savedAdvertisementRepository,
                                     UserRepository userRepository,
                                     AdvertisementRepository advertisementRepository, AdvertisementMapper advertisementMapper) {
        this.savedAdvertisementRepository = savedAdvertisementRepository;
        this.userRepository = userRepository;
        this.advertisementRepository = advertisementRepository;
        this.advertisementMapper = advertisementMapper;
    }

    @Transactional
    public void saveAdvertisement(Integer userId, Integer advertisementId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        Advertisement advertisement = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new AdvertisementNotFoundException(advertisementId));

        if (savedAdvertisementRepository.findByUserAndAdvertisement(user, advertisement).isPresent()) {
            return;
        }

        SavedAdvertisement savedAdvertisement = new SavedAdvertisement();
        savedAdvertisement.setUser(user);
        savedAdvertisement.setAdvertisement(advertisement);
        savedAdvertisementRepository.save(savedAdvertisement);
    }

    @Transactional
    public void unsaveAdvertisement(Integer userId, Integer advertisementId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        Advertisement advertisement = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new AdvertisementNotFoundException(advertisementId));

        savedAdvertisementRepository.deleteByUserAndAdvertisement(user, advertisement);
    }

    public List<AdvertisementResponseDto> getSavedAdvertisements(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        List<SavedAdvertisement> savedAds = savedAdvertisementRepository.findByUser(user);

        List<Advertisement> advertisements = savedAds.stream()
                .map(SavedAdvertisement::getAdvertisement)
                .toList();

        return advertisementMapper.toResponseDtoList(advertisements);
    }
}
