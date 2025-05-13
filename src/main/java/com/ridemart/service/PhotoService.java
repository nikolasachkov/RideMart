package com.ridemart.service;

import com.ridemart.entity.Advertisement;
import com.ridemart.entity.Photo;
import com.ridemart.exception.AdvertisementNotFoundException;
import com.ridemart.repository.AdvertisementRepository;
import com.ridemart.repository.PhotoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final AdvertisementRepository advertisementRepository;

    @Transactional
    public void addPhotosToAdvertisement(Integer advertisementId, List<String> photoUrls) {
        log.info("Adding photos to advertisement {}", advertisementId);
        Advertisement advertisement = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new AdvertisementNotFoundException(advertisementId));

        List<Photo> photos = photoUrls.stream()
                .map(url -> new Photo(null, advertisement, url))
                .collect(Collectors.toList());

        photoRepository.saveAll(photos);
    }

    @Transactional
    public void removePhotosFromAdvertisement(Integer advertisementId, List<String> photoUrlsToRemove) {
        log.info("Removing selected photos from advertisement {}", advertisementId);
        List<Photo> photosToRemove = photoRepository.findByAdvertisementId(advertisementId).stream()
                .filter(photo -> photoUrlsToRemove.contains(photo.getPhotoUrl()))
                .collect(Collectors.toList());

        photoRepository.deleteAll(photosToRemove);
    }

    @Transactional
    public void updatePhotosForAdvertisement(Integer advertisementId, List<String> updatedPhotoUrls) {
        log.info("Updating photos for advertisement {}", advertisementId);
        List<Photo> existingPhotos = photoRepository.findByAdvertisementId(advertisementId);

        List<String> existingPhotoUrls = existingPhotos.stream()
                .map(Photo::getPhotoUrl)
                .collect(Collectors.toList());

        List<Photo> photosToRemove = existingPhotos.stream()
                .filter(photo -> !updatedPhotoUrls.contains(photo.getPhotoUrl()))
                .collect(Collectors.toList());

        List<String> newPhotosToAdd = updatedPhotoUrls.stream()
                .filter(url -> !existingPhotoUrls.contains(url))
                .collect(Collectors.toList());

        if (!photosToRemove.isEmpty()) {
            photoRepository.deleteAll(photosToRemove);
            photoRepository.flush();
        }

        if (!newPhotosToAdd.isEmpty()) {
            addPhotosToAdvertisement(advertisementId, newPhotosToAdd);
        }
    }
}