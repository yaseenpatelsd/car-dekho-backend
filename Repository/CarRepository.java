package com.car.dekho.car.dekho.Repository;

import com.car.dekho.car.dekho.Entty.CarEntity;
import com.car.dekho.car.dekho.Entty.RazorPayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<CarEntity,Long>,JpaSpecificationExecutor<CarEntity>{

}
