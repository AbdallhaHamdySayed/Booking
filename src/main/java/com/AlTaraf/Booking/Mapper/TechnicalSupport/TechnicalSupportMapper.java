package com.AlTaraf.Booking.Mapper.TechnicalSupport;

import com.AlTaraf.Booking.Payload.response.TechnicalSupport.TechnicalSupportResponse;
import com.AlTaraf.Booking.Entity.TechnicalSupport.TechnicalSupport;
import com.AlTaraf.Booking.Payload.request.TechnicalSupport.TechnicalSupportRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

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