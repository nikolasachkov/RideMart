package com.ridemart.service;

import com.ridemart.entity.Advertisement;
import com.ridemart.entity.Photo;
import com.ridemart.repository.AdvertisementRepository;
import com.ridemart.repository.PhotoRepository;
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
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PhotoServiceTest {

    @Mock
    private PhotoRepository photoRepository;

    @Mock
    private AdvertisementRepository advertisementRepository;

    @InjectMocks
    private PhotoService photoService;

    private Advertisement testAdvertisement;
    private Photo testPhoto1;
    private Photo testPhoto2;

    @BeforeEach
    void setUp() {
        testAdvertisement = new Advertisement();
        testAdvertisement.setId(1);

        testPhoto1 = new Photo();
        testPhoto1.setId(1);
        testPhoto1.setAdvertisement(testAdvertisement);
        testPhoto1.setPhotoUrl("http://example.com/photo1.jpg");

        testPhoto2 = new Photo();
        testPhoto2.setId(2);
        testPhoto2.setAdvertisement(testAdvertisement);
        testPhoto2.setPhotoUrl("http://example.com/photo2.jpg");
    }

    @Test
    void addPhotosToAdvertisement_ShouldSaveNewPhotos() {
        List<String> photoUrls = Arrays.asList(
                "http://example.com/photo1.jpg",
                "http://example.com/photo2.jpg"
        );
        when(advertisementRepository.findById(1)).thenReturn(Optional.of(testAdvertisement));
        when(photoRepository.saveAll(anyList())).thenReturn(Arrays.asList(testPhoto1, testPhoto2));

        photoService.addPhotosToAdvertisement(1, photoUrls);

        verify(advertisementRepository).findById(1);
        verify(photoRepository).saveAll(anyList());
    }

    @Test
    void removePhotosFromAdvertisement_ShouldDeleteSelectedPhotos() {
        List<String> photoUrlsToRemove = Arrays.asList("http://example.com/photo1.jpg");
        List<Photo> photos = Arrays.asList(testPhoto1, testPhoto2);
        when(photoRepository.findByAdvertisementId(1)).thenReturn(photos);

        photoService.removePhotosFromAdvertisement(1, photoUrlsToRemove);

        verify(photoRepository).findByAdvertisementId(1);
        verify(photoRepository).deleteAll(anyList());
    }

    @Test
    void updatePhotosForAdvertisement_ShouldUpdatePhotos() {
        List<String> updatedPhotoUrls = Arrays.asList(
                "http://example.com/photo1.jpg",
                "http://example.com/photo3.jpg"
        );
        List<Photo> existingPhotos = Arrays.asList(testPhoto1, testPhoto2);
        when(photoRepository.findByAdvertisementId(1)).thenReturn(existingPhotos);
        when(advertisementRepository.findById(1)).thenReturn(Optional.of(testAdvertisement));
        when(photoRepository.saveAll(anyList())).thenReturn(Arrays.asList(testPhoto1));

        photoService.updatePhotosForAdvertisement(1, updatedPhotoUrls);

        verify(photoRepository).findByAdvertisementId(1);
        verify(photoRepository).deleteAll(anyList());
        verify(photoRepository).saveAll(anyList());
    }
} 