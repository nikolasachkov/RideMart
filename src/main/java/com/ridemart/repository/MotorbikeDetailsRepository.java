package com.ridemart.repository;

import com.ridemart.entity.MotorbikeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MotorbikeDetailsRepository extends JpaRepository<MotorbikeDetails, Integer> {
    @Query("SELECT m FROM MotorbikeDetails m WHERE m.id = :advertisementId")
    MotorbikeDetails findByAdvertisementId(@Param("advertisementId") Integer advertisementId);
    void deleteById(Integer id);
}
