package com.AlTaraf.Booking.Controller.TechnicalSupport;

import com.AlTaraf.Booking.Entity.TechnicalSupport.TechnicalSupport;
import com.AlTaraf.Booking.Entity.TechnicalSupport.TechnicalSupportForUnits;
import com.AlTaraf.Booking.Mapper.TechnicalSupport.TechnicalSupportMapper;
import com.AlTaraf.Booking.Mapper.TechnicalSupport.TechnicalSupportUnitsMapper;
import com.AlTaraf.Booking.Payload.request.TechnicalSupport.TechnicalSupportRequest;
import com.AlTaraf.Booking.Payload.request.TechnicalSupport.TechnicalSupportUnitsRequest;
import com.AlTaraf.Booking.Payload.response.ApiResponse;
import com.AlTaraf.Booking.Service.TechnicalSupport.TechnicalSupportService;
import com.AlTaraf.Booking.Service.TechnicalSupport.TechnicalSupportUnitsService;
import com.AlTaraf.Booking.Service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    UserService userService;

    @Autowired
    private MessageSource messageSource;

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
