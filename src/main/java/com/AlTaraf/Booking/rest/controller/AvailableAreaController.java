package com.AlTaraf.Booking.rest.controller;

import com.AlTaraf.Booking.database.entity.AvailableArea;
import com.AlTaraf.Booking.rest.dto.ApiResponse;
import com.AlTaraf.Booking.service.AvailableAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api")
public class AvailableAreaController {
    @Autowired
    AvailableAreaService availableAreaService;

    @Autowired
    MessageSource messageSource;

    @GetMapping("/get-Available-Area")
    public ResponseEntity<?> getAllAvailableArea(@RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader) {

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

        List<AvailableArea> availableAreaList = availableAreaService.getAllAvailableArea();

        if (!availableAreaList.isEmpty()) {
            return new ResponseEntity<>(availableAreaList, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, messageSource.getMessage("no_content.message", null, LocaleContextHolder.getLocale())));
        }
    }
}