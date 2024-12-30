package com.AlTaraf.Booking.rest.mapper;

import com.AlTaraf.Booking.database.entity.TechnicalSupport;
import com.AlTaraf.Booking.rest.dto.TechnicalSupportRequest;
import com.AlTaraf.Booking.rest.dto.TechnicalSupportResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TechnicalSupportMapper {

    TechnicalSupportMapper INSTANCE = Mappers.getMapper(TechnicalSupportMapper.class);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "message", source = "message")
    @Mapping(target = "name", source = "name")
    TechnicalSupport toEntity(TechnicalSupportRequest technicalSupportRequest);


    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", source = "email")
    @Mapping(source = "user.fileForProfile", target = "fileForProfileDTO")
    @Mapping(target = "message", source = "message")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "seen", source = "seen")
    @Mapping(target = "elapsedTime", source = "elapsedTime")
    TechnicalSupportResponse toDto(TechnicalSupport technicalSupport);

    TechnicalSupportResponse toDTO(TechnicalSupport technicalSupport);
}