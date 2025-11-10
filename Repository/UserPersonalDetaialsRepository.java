package com.car.dekho.car.dekho.Repository;

import com.car.dekho.car.dekho.Entty.UserPersonalDetailEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserPersonalDetaialsRepository extends JpaRepository<UserPersonalDetailEntity,Long>, JpaSpecificationExecutor
        <UserPersonalDetailEntity> {
    List<UserPersonalDetailEntity> deleteByUser_Username(String username);
    Optional<UserPersonalDetailEntity> findByUser_Username(String username);

}
