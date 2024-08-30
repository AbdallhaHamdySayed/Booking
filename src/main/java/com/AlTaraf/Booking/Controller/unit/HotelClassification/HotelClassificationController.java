package com.AlTaraf.Booking.Controller.unit.HotelClassification;

import com.AlTaraf.Booking.Entity.unit.hotelClassification.HotelClassification;
import com.AlTaraf.Booking.Payload.response.ApiResponse;
import com.AlTaraf.Booking.Service.unit.hotelClassification.HotelClassificationService;
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
public class HotelClassificationController {

    @Autowired
    HotelClassificationService hotelClassificationService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/get-All-Hotel-Classifications")
    public ResponseEntity<?> getAllHotelClassifications(@RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader) {

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

        List<HotelClassification> classifications = hotelClassificationService.getAllHotelClassification();

        if (!classifications.isEmpty()) {
            return new ResponseEntity<>(classifications, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, messageSource.getMessage("no_content.message", null, LocaleContextHolder.getLocale())));
        }
    }

}
