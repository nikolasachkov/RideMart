package com.ridemart.repository;

import com.ridemart.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Integer>, JpaSpecificationExecutor<Advertisement> {
}

