package com.car.dekho.car.dekho.Specification;

import com.car.dekho.car.dekho.Entty.UserPersonalDetailEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class PersonalDetailSpecification {

    public static Specification<UserPersonalDetailEntity> findByName(String fullName){
        return (root, query, cb) -> cb.like(cb.lower(root.get("fullName")), "%" + fullName.toLowerCase() + "%");
    }
    public static Specification<UserPersonalDetailEntity> findByMobileNo(Long mobileNo){
        return (root, query, cb) -> cb.equal(root.get("mobileNo"), mobileNo);
    }
}
