package com.car.dekho.car.dekho.Specification;

import com.car.dekho.car.dekho.Entty.OfferEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class OfferSpecification {

    public static Specification<OfferEntity> findCity(String city){
        return ((root, query, criteriaBuilder) -> city==null? null : criteriaBuilder.equal(root.get("city"),city));
    }

    public static Specification<OfferEntity> hasOfferName(String offererName){
        return ((root, query, criteriaBuilder) -> offererName==null? null: criteriaBuilder.equal(root.get("offererName"), offererName));
    }

    public static Specification<OfferEntity> hasPriceBetween(Double min, Double max){
        return (root, query, criteriaBuilder) -> {
            if (min==null && max==null) return null;
            if (min!=null && max!=null) return criteriaBuilder.between(root.get("price"), min,max);
            if (min!=null) return criteriaBuilder.greaterThanOrEqualTo(root.get("price"),min);
            return criteriaBuilder.lessThanOrEqualTo(root.get("price"),max);
        };
    }
}
