package com.ridemart.service;

import com.ridemart.entity.Advertisement;
import com.ridemart.entity.Photo;
import com.ridemart.exception.AdvertisementNotFoundException;
import com.ridemart.repository.AdvertisementRepository;
import com.ridemart.repository.PhotoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final AdvertisementRepository advertisementRepository;

    public PhotoService(PhotoRepository photoRepository, AdvertisementRepository advertisementRepository) {
        this.photoRepository = photoRepository;
        this.advertisementRepository = advertisementRepository;
    }

    @Transactional
    public void addPhotosToAdvertisement(Integer advertisementId, List<String> photoUrls) {
        Advertisement advertisement = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new AdvertisementNotFoundException(advertisementId));

        List<Photo> photos = photoUrls.stream()
                .map(url -> {
                    Photo photo = new Photo();
                    photo.setPhotoUrl(url);
                    photo.setAdvertisement(advertisement);
                    return photo;
                })
                .collect(Collectors.toList());

        photoRepository.saveAll(photos);
    }

    @Transactional
    public void removePhotosFromAdvertisement(Integer advertisementId, List<String> photoUrlsToRemove) {
        List<Photo> photosToRemove = photoRepository.findByAdvertisementId(advertisementId).stream()
                .filter(photo -> photoUrlsToRemove.contains(photo.getPhotoUrl()))
                .collect(Collectors.toList());

        photoRepository.deleteAll(photosToRemove);
    }

    @Transactional
    public void updatePhotosForAdvertisement(Integer advertisementId, List<String> updatedPhotoUrls) {
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