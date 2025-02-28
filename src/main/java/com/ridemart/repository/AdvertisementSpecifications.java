package com.ridemart.repository;

import com.ridemart.dto.AdvertisementFilterDto;
import com.ridemart.entity.Advertisement;
import com.ridemart.entity.MotorbikeDetails;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

public class AdvertisementSpecifications {
    public static Specification<Advertisement> withFilters(AdvertisementFilterDto filter) {
        return (root, query, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();

            if (filter.getCity() != null && !filter.getCity().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("city"), filter.getCity()));
            }

            Join<Advertisement, MotorbikeDetails> motorbikeDetailsJoin = root.join("motorbikeDetails");

            if (filter.getMake() != null) {
                predicates.add(criteriaBuilder.equal(motorbikeDetailsJoin.get("make"), filter.getMake()));
            }
            if (filter.getModel() != null && !filter.getModel().isEmpty()) {
                predicates.add(criteriaBuilder.equal(motorbikeDetailsJoin.get("model"), filter.getModel()));
            }
            if (filter.getMinYear() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(motorbikeDetailsJoin.get("year"), filter.getMinYear()));
            }
            if (filter.getMaxYear() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(motorbikeDetailsJoin.get("year"), filter.getMaxYear()));
            }
            if (filter.getMinPrice() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(motorbikeDetailsJoin.get("price"), filter.getMinPrice()));
            }
            if (filter.getMaxPrice() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(motorbikeDetailsJoin.get("price"), filter.getMaxPrice()));
            }
            if (filter.getMinMileage() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(motorbikeDetailsJoin.get("mileage"), filter.getMinMileage()));
            }
            if (filter.getMaxMileage() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(motorbikeDetailsJoin.get("mileage"), filter.getMaxMileage()));
            }
            if (filter.getMinEngineSize() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(motorbikeDetailsJoin.get("engineSize"), filter.getMinEngineSize()));
            }
            if (filter.getMaxEngineSize() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(motorbikeDetailsJoin.get("engineSize"), filter.getMaxEngineSize()));
            }
            if (filter.getEngineType() != null) {
                predicates.add(criteriaBuilder.equal(motorbikeDetailsJoin.get("engineType"), filter.getEngineType()));
            }
            if (filter.getMotorbikeType() != null) {
                predicates.add(criteriaBuilder.equal(motorbikeDetailsJoin.get("motorbikeType"), filter.getMotorbikeType()));
            }
            if (filter.getFuelSystemType() != null) {
                predicates.add(criteriaBuilder.equal(motorbikeDetailsJoin.get("fuelSystemType"), filter.getFuelSystemType()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
