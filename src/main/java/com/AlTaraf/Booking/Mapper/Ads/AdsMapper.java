package com.AlTaraf.Booking.Mapper.Ads;


import com.AlTaraf.Booking.Entity.Ads.Ads;
import com.AlTaraf.Booking.Entity.File.FileForAds;
import com.AlTaraf.Booking.Payload.request.Ads.AdsRequestDto;
import com.AlTaraf.Booking.Payload.request.Ads.AdsResponseDto;
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