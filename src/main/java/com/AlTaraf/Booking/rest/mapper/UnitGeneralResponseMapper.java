package com.AlTaraf.Booking.rest.mapper;

import com.AlTaraf.Booking.database.entity.*;
import com.AlTaraf.Booking.rest.dto.AvailablePeriodsDto;
import com.AlTaraf.Booking.rest.dto.FeatureForHallsDto;
import com.AlTaraf.Booking.rest.dto.UnitGeneralResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UnitGeneralResponseMapper {
    @Mapping(source = "id", target = "unitId")
    @Mapping(source = "unitType", target = "unitType")
    @Mapping(source = "user.deviceToken", target = "deviceToken")
    @Mapping(target = "imagePaths", expression = "java(extractFileImagePaths(unit.getFileForUnits()))")
    @Mapping(target = "comments", expression = "java(extractComment(unit.getComments()))")
    @Mapping(target = "videoPaths", expression = "java(extractFirstFileVideoPath(unit.getFileForUnits()))")
    @Mapping(source = "nameUnit", target = "nameUnit")
    @Mapping(source = "youtubeUrl", target = "youtubeUrl")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "city", target = "cityDtoSample")
    @Mapping(source = "region", target = "regionDto")
    @Mapping(source = "accommodationType", target = "accommodationType")
    @Mapping(source = "typesOfEventHalls", target = "typesOfEventHalls")
    @Mapping(source = "hotelClassification", target = "hotelClassification")
    @Mapping(source = "typeOfApartment", target = "typeOfApartment")
    @Mapping(source = "roomAvailableSet", target = "roomAvailables")
    @Mapping(source = "basicFeaturesSet", target = "features")
    @Mapping(source = "subFeaturesSet", target = "subFeatures")
    @Mapping(source = "availableAreaSet", target = "availableAreas")
    @Mapping(source = "latForMapping", target = "latForMapping")
    @Mapping(source = "longForMapping", target = "longForMapping")
    @Mapping(source = "capacityHalls", target = "capacityHalls")
    @Mapping(source = "featuresHallsSet", target = "featuresHallsDto", qualifiedByName = "mapEntityToFeaturesHallsDto")
    @Mapping(source = "availablePeriodsHallsSet", target = "availablePeriodsHallsDto", qualifiedByName = "mapEntityToAvailablePeriodsHallsDto")
    @Mapping(source = "roomDetailsForAvailableAreaList", target = "roomDetailsForAvailableAreaList")
    @Mapping(source = "roomDetails", target = "roomDetails")
    @Mapping(source = "oldPriceHall", target = "oldPriceHall")
    @Mapping(source = "newPriceHall", target = "newPriceHall")
    @Mapping(source = "chaletOldPrice", target = "chaletOldPrice")
    @Mapping(source = "chaletNewPrice", target = "chaletNewPrice")
    @Mapping(source = "apartmentOldPrice", target = "apartmentOldPrice")
    @Mapping(source = "apartmentNewPrice", target = "apartmentNewPrice")
    @Mapping(source = "loungeOldPrice", target = "loungeOldPrice")
    @Mapping(source = "loungeNewPrice", target = "loungeNewPrice")
    @Mapping(source = "evaluation.name", target = "evaluationName")
    @Mapping(source = "evaluation.arabicName", target = "evaluationArabicName")
    @Mapping(source = "price", target = "price", defaultValue = "0")
    @Mapping(source = "oldPrice", target = "oldPrice", defaultValue = "0")
    @Mapping(source = "commission", target = "commission")
    @Mapping(source = "dateOfArrival", target = "dateOfArrival", qualifiedByName = "formatDate")
    @Mapping(source = "departureDate", target = "departureDate", qualifiedByName = "formatDate")
    @Mapping(source = "adultsAllowed", target = "adultsAllowed")
    @Mapping(source = "childrenAllowed", target = "childrenAllowed")
    @Mapping(source = "favorite", target = "favorite")
    @Mapping(source = "totalEvaluation", target = "totalEvaluation")
    UnitGeneralResponseDto toResponseDto(Unit unit);

    @Named("mapEntityToFeaturesHallsDto")
    default Set<FeatureForHallsDto> mapEntityToFeaturesHallsDto(Set<FeatureForHalls> featuresHallsSet) {
        return featuresHallsSet.stream()
                .map(feature -> new FeatureForHallsDto(feature.getId(), feature.getName(), feature.getArabicName())) // Assuming FeatureForHallsDto has constructor taking id and name
                .collect(Collectors.toSet());
    }

    @Named("mapEntityToAvailablePeriodsHallsDto")
    default Set<AvailablePeriodsDto> mapEntityToAvailablePeriodsHallsDto(Set<AvailablePeriods> availablePeriodsHallsSet) {
        return availablePeriodsHallsSet.stream()
                .map(period -> new AvailablePeriodsDto(period.getId(), period.getName(), period.getArabicName())) // Assuming AvailablePeriodsDto has constructor taking id and name
                .collect(Collectors.toSet());
    }

    default List<String> extractFileImagePaths(List<FileForUnit> fileForUnits) {
        if (fileForUnits == null || fileForUnits.isEmpty()) {
            return Collections.emptyList();
        }
        return fileForUnits.stream()
                .sorted(Comparator.comparing(FileForUnit::getCreatedDate)
                        .thenComparing(FileForUnit::getCreatedTime))
                .map(FileForUnit::getFileImageUrl)
                .filter(Objects::nonNull) // To ensure no null file paths are included
                .collect(Collectors.toList());
    }

    default List<Comment> extractComment(List<Comment> comments) {
        if (comments == null) {
            return Collections.emptyList();
        }
        return comments.stream()
                .sorted(Comparator.comparing(Comment::getCreatedDate)
                        .thenComparing(Comment::getCreatedTime))
                .collect(Collectors.toList());
    }

    default String extractFirstFileVideoPath(List<FileForUnit> fileForUnits) {
        return fileForUnits.stream()
                .map(FileForUnit::getFileVideoUrl) // Or whatever method retrieves the video path
                .filter(url -> url != null) // Exclude null URLs
                .findFirst()
                .orElse(null);
    }

    @Named("formatDate")
    static String formatDate(LocalDate date) {
        return date != null ? date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
    }
}
