package com.car.dekho.car.dekho.Repository;

import com.car.dekho.car.dekho.Entty.RazorPayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface RazorPayRepository extends JpaRepository<RazorPayEntity,Long> {
    Optional<RazorPayEntity> findByStatus(String status);
}
