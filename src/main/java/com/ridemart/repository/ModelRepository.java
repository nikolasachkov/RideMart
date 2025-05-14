package com.ridemart.repository;

import com.ridemart.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModelRepository extends JpaRepository<Model, Integer> {
    Optional<Model> findByName(String name);
    List<Model> findByMakeId(Integer makeId);
    Optional<Model> findByMakeIdAndName(Integer makeId, String name);
}
