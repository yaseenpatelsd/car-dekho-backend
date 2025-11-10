package com.car.dekho.car.dekho.Repository;

import com.car.dekho.car.dekho.Entty.OfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;


public interface OfferRepository extends JpaRepository<OfferEntity,Long>,JpaSpecificationExecutor<OfferEntity>{
    Optional<OfferEntity> findByUser_UsernameAndUserPersonalDetails_Id(String username, long id);
    List<OfferEntity> findByCar_Id(Long carId);

}
