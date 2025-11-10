package com.car.dekho.car.dekho.Specification;

import com.car.dekho.car.dekho.Entty.CarEntity;
import com.car.dekho.car.dekho.Entty.UserEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CarSpecification {

    public static Specification<CarEntity> findByName(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isEmpty()) return null;
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<CarEntity> findByBrand(String brand) {
        return (root, query, cb) -> {
            if (brand == null || brand.isEmpty()) return null;
            return cb.like(cb.lower(root.get("brand")), "%" + brand.toLowerCase() + "%");
        };
    }

    public static Specification<CarEntity> findByBetweenPrice(Double minPrice, Double maxPrice) {
        return (root, query, cb) -> {
            if (minPrice == null && maxPrice == null) return null;
            if (minPrice != null && maxPrice != null) return cb.between(root.get("price"), minPrice, maxPrice);
            if (minPrice != null) return cb.greaterThanOrEqualTo(root.get("price"), minPrice);
            return cb.lessThanOrEqualTo(root.get("price"), maxPrice);
        };
    }
}