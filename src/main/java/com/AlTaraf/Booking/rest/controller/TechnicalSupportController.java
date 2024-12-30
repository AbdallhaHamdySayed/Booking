package com.AlTaraf.Booking.rest.controller;

import com.AlTaraf.Booking.database.entity.TechnicalSupport;
import com.AlTaraf.Booking.database.entity.TechnicalSupportForUnits;
import com.AlTaraf.Booking.rest.dto.ApiResponse;
import com.AlTaraf.Booking.rest.dto.TechnicalSupportRequest;
import com.AlTaraf.Booking.rest.dto.TechnicalSupportUnitsRequest;
import com.AlTaraf.Booking.rest.mapper.TechnicalSupportMapper;
import com.AlTaraf.Booking.rest.mapper.TechnicalSupportUnitsMapper;
import com.AlTaraf.Booking.service.TechnicalSupportService;
import com.AlTaraf.Booking.service.TechnicalSupportUnitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/technical-support")
public class TechnicalSupportController {

    @Autowired
    TechnicalSupportService technicalSupportService;

    @Autowired
    TechnicalSupportUnitsService technicalSupportUnitsService;

    @Autowired
    MessageSource messageSource;

    @PostMapping("/submit")
    public ResponseEntity<?> submitTechnicalSupport( @RequestBody TechnicalSupportRequest technicalSupportRequest,
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

            // You might want to perform additional validation or business logic here
            TechnicalSupport technicalSupport = TechnicalSupportMapper.INSTANCE.toEntity(technicalSupportRequest);
            technicalSupportService.saveTechnicalSupport(technicalSupport);

            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200,  messageSource.getMessage("Technical_Support.message", null, LocaleContextHolder.getLocale())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(400, messageSource.getMessage("Technical_Support_Error.message", null, LocaleContextHolder.getLocale())));
        }
    }

    @PostMapping("Technical-Support-Units/submit")
    public ResponseEntity<?> submitTechnicalSupportUnits( @RequestBody TechnicalSupportUnitsRequest technicalSupportUnitsRequest,
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

            // You might want to perform additional validation or business logic here
            TechnicalSupportForUnits technicalSupportForUnits = TechnicalSupportUnitsMapper.INSTANCE.toEntity(technicalSupportUnitsRequest);
            technicalSupportUnitsService.saveTechnicalSupport(technicalSupportForUnits);

            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, messageSource.getMessage("Technical_Support.message", null, LocaleContextHolder.getLocale())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(400, messageSource.getMessage("Technical_Support_Error.message", null, LocaleContextHolder.getLocale())));
        }
    }

}
