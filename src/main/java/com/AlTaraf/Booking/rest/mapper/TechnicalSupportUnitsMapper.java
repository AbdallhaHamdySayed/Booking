package com.AlTaraf.Booking.rest.mapper;

import com.AlTaraf.Booking.database.entity.TechnicalSupportForUnits;
import com.AlTaraf.Booking.rest.dto.TechnicalSupportUnitsRequest;
import com.AlTaraf.Booking.rest.dto.TechnicalSupportUnitsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TechnicalSupportUnitsMapper {

    TechnicalSupportUnitsMapper INSTANCE = Mappers.getMapper(TechnicalSupportUnitsMapper.class);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "unit.id", source = "unitId")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "message", source = "message")
    @Mapping(target = "name", source = "name")
    TechnicalSupportForUnits toEntity(TechnicalSupportUnitsRequest technicalSupportUnitsRequest);


    @Mapping(target = "id", source = "id")
    @Mapping(source = "user.fileForProfile", target = "fileForProfileDTO")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "unitId", source = "unit.id")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "message", source = "message")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "seen", source = "seen")
    @Mapping(target = "elapsedTime", source = "elapsedTime")
    TechnicalSupportUnitsResponse toDto(TechnicalSupportForUnits technicalSupportUnitsRequest);

    TechnicalSupportUnitsResponse toDTO(TechnicalSupportForUnits technicalSupportForUnits);
}