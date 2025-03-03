package com.ridemart.repository;

import com.ridemart.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface AdvertisementRepository extends JpaRepository<Advertisement, Integer>, JpaSpecificationExecutor<Advertisement> {
    List<Advertisement> findByUserId(Integer userId);
}

