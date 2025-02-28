package com.ridemart.repository;

import com.ridemart.entity.MotorbikeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MotorbikeDetailsRepository extends JpaRepository<MotorbikeDetails, Integer> {
    void deleteById(Integer id);
}
