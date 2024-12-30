package com.AlTaraf.Booking.rest.mapper;

import com.AlTaraf.Booking.database.entity.Ads;
import com.AlTaraf.Booking.database.entity.FileForAds;
import com.AlTaraf.Booking.rest.dto.AdsRequestDto;
import com.AlTaraf.Booking.rest.dto.AdsResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdsMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "unit.id", target = "unitId")
    @Mapping(expression = "java(extractImagePath(ads.getFileForAds()))", target = "imagePath")
    @Mapping(source = "user.id", target = "userId")
    AdsResponseDto toDto(Ads ads);

    @Mapping(source = "unitId", target = "unit.id")
    @Mapping(source = "userId", target = "user.id")
    Ads toEntity(AdsRequestDto adsDto);

    default String extractImagePath(FileForAds fileForAds) {
        return fileForAds != null ? fileForAds.getFileDownloadUri() : null;
    }

}