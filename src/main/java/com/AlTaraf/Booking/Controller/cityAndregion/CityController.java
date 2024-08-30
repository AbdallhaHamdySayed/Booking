package com.AlTaraf.Booking.Controller.cityAndregion;

import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.CityDto;
import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.RegionDto;
import com.AlTaraf.Booking.Entity.cityAndregion.City;
import com.AlTaraf.Booking.Entity.cityAndregion.Region;
import com.AlTaraf.Booking.Mapper.city.CityMapper;
import com.AlTaraf.Booking.Payload.response.ApiResponse;
import com.AlTaraf.Booking.Service.cityAndRegion.CityService;
import com.AlTaraf.Booking.Service.cityAndRegion.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    @Autowired
    CityService cityService;

    @Autowired
    CityMapper cityMapper;

    @Autowired
    RegionService regionService;

    @Autowired
    MessageSource messageSource;

    @PostMapping("add")
    public ResponseEntity<?> createCity(@RequestBody CityDto cityDto,
                                        @RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader) {
        try {
            Locale locale = LocaleContextHolder.getLocale(); // Default to the locale context holder's locale

            if (acceptLanguageHeader != null && !acceptLanguageHeader.isEmpty()) {
                try {
                    List<Locale.LanguageRange> languageRanges = Locale.LanguageRange.parse(acceptLanguageHeader);
                    if (!languageRanges.isEmpty()) {
                        locale = Locale.forLanguageTag(languageRanges.get(0).getRange());
                    }
                } catch (IllegalArgumentException e) {
                    // Handle the exception if needed
                    System.out.println("IllegalArgumentException: " + e);
                }
            }
            cityService.createCity(cityDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(200, messageSource.getMessage("city_created_successful.message", null, LocaleContextHolder.getLocale())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, messageSource.getMessage("internal_server_error.message", null, LocaleContextHolder.getLocale())));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable("id") Long cityId,
                                        @RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader) {
        try {
            Locale locale = LocaleContextHolder.getLocale(); // Default to the locale context holder's locale

            if (acceptLanguageHeader != null && !acceptLanguageHeader.isEmpty()) {
                try {
                    List<Locale.LanguageRange> languageRanges = Locale.LanguageRange.parse(acceptLanguageHeader);
                    if (!languageRanges.isEmpty()) {
                        locale = Locale.forLanguageTag(languageRanges.get(0).getRange());
                    }
                } catch (IllegalArgumentException e) {
                    // Handle the exception if needed
                    System.out.println("IllegalArgumentException: " + e);
                }
            }
            cityService.deleteCity(cityId);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, messageSource.getMessage("city_deleted_successful.message", null, LocaleContextHolder.getLocale())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageSource.getMessage("internal_server_error.message", null, LocaleContextHolder.getLocale()));
        }
    }

    @DeleteMapping("/region/{id}")
    public ResponseEntity<?> deleteRegion(@PathVariable("id") Long regionId,
                                          @RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader) {
        try {
            Locale locale = LocaleContextHolder.getLocale(); // Default to the locale context holder's locale

            if (acceptLanguageHeader != null && !acceptLanguageHeader.isEmpty()) {
                try {
                    List<Locale.LanguageRange> languageRanges = Locale.LanguageRange.parse(acceptLanguageHeader);
                    if (!languageRanges.isEmpty()) {
                        locale = Locale.forLanguageTag(languageRanges.get(0).getRange());
                    }
                } catch (IllegalArgumentException e) {
                    // Handle the exception if needed
                    System.out.println("IllegalArgumentException: " + e);
                }
            }
            regionService.deleteRegion(regionId);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, messageSource.getMessage("region_deleted_successful.message", null, LocaleContextHolder.getLocale())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, messageSource.getMessage("internal_server_error.message", null, LocaleContextHolder.getLocale())));
        }
    }

    @PostMapping("{cityId}/add-region")
    public ResponseEntity<?> addRegionToCity(@PathVariable("cityId") Long cityId, @RequestBody RegionDto regionDto,
                                             @RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader) {
        try {
            Locale locale = LocaleContextHolder.getLocale(); // Default to the locale context holder's locale

            if (acceptLanguageHeader != null && !acceptLanguageHeader.isEmpty()) {
                try {
                    List<Locale.LanguageRange> languageRanges = Locale.LanguageRange.parse(acceptLanguageHeader);
                    if (!languageRanges.isEmpty()) {
                        locale = Locale.forLanguageTag(languageRanges.get(0).getRange());
                    }
                } catch (IllegalArgumentException e) {
                    // Handle the exception if needed
                    System.out.println("IllegalArgumentException: " + e);
                }
            }
            regionService.addRegionToCity(cityId, regionDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(200, messageSource.getMessage("region_created_successful.message", null, LocaleContextHolder.getLocale())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, messageSource.getMessage("internal_server_error.message", null, LocaleContextHolder.getLocale())));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCities(@RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader) {
        Locale locale = LocaleContextHolder.getLocale(); // Default to the locale context holder's locale

        if (acceptLanguageHeader != null && !acceptLanguageHeader.isEmpty()) {
            try {
                List<Locale.LanguageRange> languageRanges = Locale.LanguageRange.parse(acceptLanguageHeader);
                if (!languageRanges.isEmpty()) {
                    locale = Locale.forLanguageTag(languageRanges.get(0).getRange());
                }
            } catch (IllegalArgumentException e) {
                // Handle the exception if needed
                System.out.println("IllegalArgumentException: " + e);
            }
        }
        List<CityDto> cities = cityService.getAllCities();
        if (cities.isEmpty()) {
            ApiResponse response = new ApiResponse(204, messageSource.getMessage("no_content.message", null, LocaleContextHolder.getLocale()));

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } else {
            return ResponseEntity.ok(cities);
        }
    }

    @PutMapping("/{cityId}/regions/{regionId}")
    public ResponseEntity<Region> updateRegionInCity(
            @PathVariable Long cityId,
            @PathVariable Long regionId,
            @RequestBody RegionDto RegionDto) {

        Region updatedRegion = cityService.updateRegionInCity(cityId, regionId, RegionDto);
        return new ResponseEntity<>(updatedRegion, HttpStatus.OK);
    }

    @PutMapping("/cities/{cityId}/English-Name")
    public ResponseEntity<?> updateCityEnglishName(
            @PathVariable Long cityId,
            @RequestParam String newName
    ) {
        Optional<City> optionalCity = cityService.findById(cityId);
        if (optionalCity.isPresent()) {
            City city = optionalCity.get();
            city.setCityName(newName);
            cityService.save(city);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/cities/{cityId}/Arabic-Name")
    public ResponseEntity<?> updateCityArabicName(
            @PathVariable Long cityId,
            @RequestParam String newName
    ) {
        Optional<City> optionalCity = cityService.findById(cityId);
        if (optionalCity.isPresent()) {
            City city = optionalCity.get();
            city.setArabicCityName(newName);
            cityService.save(city);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
