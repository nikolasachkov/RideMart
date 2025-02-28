package com.ridemart.repository;

import com.ridemart.entity.SavedAdvertisement;
import com.ridemart.entity.User;
import com.ridemart.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedAdvertisementRepository extends JpaRepository<SavedAdvertisement, Integer> {
    List<SavedAdvertisement> findByUser(User user);
    Optional<SavedAdvertisement> findByUserAndAdvertisement(User user, Advertisement advertisement);
    void deleteByUserAndAdvertisement(User user, Advertisement advertisement);
}
