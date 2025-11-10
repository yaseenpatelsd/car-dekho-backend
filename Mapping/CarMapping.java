package com.car.dekho.car.dekho.Mapping;

import com.car.dekho.car.dekho.Dto.CarDto;
import com.car.dekho.car.dekho.Entty.CarEntity;
import org.springframework.stereotype.Component;

@Component
public class CarMapping {

    public CarEntity toEntity(CarDto dto) {
        CarEntity entity = new CarEntity();
        entity.setBrand(dto.getBrand());
        entity.setName(dto.getFullName());
        entity.setModel(dto.getModel());
        entity.setYear(dto.getYear());
        entity.setPrice(dto.getPrice());
        return entity;
    }

    public CarDto toDto(CarEntity entity) {
        CarDto dto = new CarDto();
        dto.setBrand(entity.getBrand());
        dto.setFullName(entity.getName());
        dto.setModel(entity.getModel());
        dto.setYear(entity.getYear());
        dto.setPrice(entity.getPrice());
        return dto;
    }
}
