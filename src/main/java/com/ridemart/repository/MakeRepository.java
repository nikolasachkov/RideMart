package com.ridemart.repository;

import com.ridemart.entity.Make;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MakeRepository extends JpaRepository<Make, Integer> {
    Optional<Make> findByName(String name);
}
