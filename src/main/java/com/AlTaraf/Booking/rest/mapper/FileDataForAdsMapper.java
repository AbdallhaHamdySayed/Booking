package com.AlTaraf.Booking.rest.mapper;

import com.AlTaraf.Booking.database.entity.FileForAds;
import com.AlTaraf.Booking.rest.dto.FileForAdsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FileDataForAdsMapper {
    @Mapping(source = "type", target = "type")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "fileDownloadUri", target = "fileDownloadUri")
    FileForAdsDTO toDTO(FileForAds fileForAds);

    @Mapping(source = "type", target = "type")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "fileDownloadUri", target = "fileDownloadUri")
    FileForAds toEntity(FileForAdsDTO fileForAdsDTO);
}