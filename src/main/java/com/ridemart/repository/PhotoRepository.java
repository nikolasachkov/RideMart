package com.ridemart.repository;

import com.ridemart.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Integer> {
    List<Photo> findByAdvertisementId(Integer advertisementId);
}
