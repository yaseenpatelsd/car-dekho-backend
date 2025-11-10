package com.car.dekho.car.dekho.Mapping;

import com.car.dekho.car.dekho.Dto.UserPersonalDetailDto;
import com.car.dekho.car.dekho.Entty.UserPersonalDetailEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonalDetailsMapping {
    public UserPersonalDetailDto toDto(UserPersonalDetailEntity entity);
    public UserPersonalDetailEntity toEntity(UserPersonalDetailDto dto);
}
