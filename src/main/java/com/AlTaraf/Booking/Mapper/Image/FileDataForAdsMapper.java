package com.AlTaraf.Booking.Mapper.Image;

import com.AlTaraf.Booking.Dto.Image.FileForAdsDTO;
import com.AlTaraf.Booking.Entity.File.FileForAds;
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