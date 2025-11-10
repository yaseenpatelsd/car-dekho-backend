package com.car.dekho.car.dekho.Mapping;

import com.car.dekho.car.dekho.Dto.OfferRequestDto;
import com.car.dekho.car.dekho.Dto.OfferResponseDto;
import com.car.dekho.car.dekho.Entty.OfferEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OfferMapping {
    public OfferEntity toEntity(OfferRequestDto  dto);
    public OfferResponseDto toDto(OfferEntity entity);
}
