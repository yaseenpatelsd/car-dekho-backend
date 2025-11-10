package com.car.dekho.car.dekho.Mapping;

import com.car.dekho.car.dekho.Dto.UserPersonalDetailDto;
import com.car.dekho.car.dekho.Entty.UserPersonalDetailEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PersonalDetailMapping {

    public UserPersonalDetailEntity toEntity(UserPersonalDetailDto toDto);
    public UserPersonalDetailDto toDto(UserPersonalDetailEntity entity);
}
