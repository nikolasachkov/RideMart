package com.ridemart.repository;

import com.ridemart.dto.AdvertisementFilterDto;
import com.ridemart.entity.Advertisement;
import com.ridemart.entity.MotorbikeDetails;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AdvertisementSpecifications {
    public static Specification<Advertisement> withFilters(AdvertisementFilterDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getCity() != null && !filter.getCity().isBlank()) {
                predicates.add(cb.equal(root.get("city"), filter.getCity()));
            }

            Join<Advertisement, MotorbikeDetails> md = root.join("motorbikeDetails");

            if (filter.getMake() != null) {
                predicates.add(cb.equal(
                        md.get("model")
                                .get("make")
                                .get("name"),
                        filter.getMake()
                ));
            }

            if (filter.getModel() != null && !filter.getModel().isBlank()) {
                predicates.add(cb.equal(
                        md.get("model")
                                .get("name"),
                        filter.getModel()
                ));
            }

            if (filter.getMinYear() != null) {
                predicates.add(cb.greaterThanOrEqualTo(md.get("year"), filter.getMinYear()));
            }
            if (filter.getMaxYear() != null) {
                predicates.add(cb.lessThanOrEqualTo(md.get("year"), filter.getMaxYear()));
            }
            if (filter.getMinPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(md.get("price"), filter.getMinPrice()));
            }
            if (filter.getMaxPrice() != null) {
                predicates.add(cb.lessThanOrEqualTo(md.get("price"), filter.getMaxPrice()));
            }
            if (filter.getMinMileage() != null) {
                predicates.add(cb.greaterThanOrEqualTo(md.get("mileage"), filter.getMinMileage()));
            }
            if (filter.getMaxMileage() != null) {
                predicates.add(cb.lessThanOrEqualTo(md.get("mileage"), filter.getMaxMileage()));
            }
            if (filter.getMinEngineSize() != null) {
                predicates.add(cb.greaterThanOrEqualTo(md.get("engineSize"), filter.getMinEngineSize()));
            }
            if (filter.getMaxEngineSize() != null) {
                predicates.add(cb.lessThanOrEqualTo(md.get("engineSize"), filter.getMaxEngineSize()));
            }
            if (filter.getEngineType() != null) {
                predicates.add(cb.equal(md.get("engineType"), filter.getEngineType()));
            }
            if (filter.getMotorbikeType() != null) {
                predicates.add(cb.equal(md.get("motorbikeType"), filter.getMotorbikeType()));
            }
            if (filter.getFuelSystemType() != null) {
                predicates.add(cb.equal(md.get("fuelSystemType"), filter.getFuelSystemType()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
