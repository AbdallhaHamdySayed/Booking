package com.AlTaraf.Booking.rest.controller;

import com.AlTaraf.Booking.database.entity.*;
import com.AlTaraf.Booking.database.repository.StatusRepository;
import com.AlTaraf.Booking.rest.dto.ApiResponse;
import com.AlTaraf.Booking.rest.dto.FileResponseMessage;
import com.AlTaraf.Booking.rest.dto.ImageUploadResponse;
import com.AlTaraf.Booking.service.FileStorageService;
import com.AlTaraf.Booking.service.UnitService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequestMapping("")
public class FileController {

    @Autowired
    FileStorageService storageService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    UnitService unitService;

    @Autowired
    StatusRepository statusUnitRepository;

    @PostMapping("/upload-file-for-unit")
    public ResponseEntity<?> uploadImages(@RequestParam(value = "files", required = false) List<MultipartFile> files,
                                          @RequestParam("userId") Long userId,
                                          @RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader) {

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

        String message1 = "";

        try {

            for (MultipartFile file : files) {
                storageService.storeForUnit(file, userId);
            }
            message1 = messageSource.getMessage("uploaded_files_successfully.message", null, LocaleContextHolder.getLocale()) + files.stream()
                    .map(MultipartFile::getOriginalFilename)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new FileResponseMessage(message1));

        }

        catch (IOException e) {
            String message = messageSource.getMessage("uploaded_files_failed.message", null, LocaleContextHolder.getLocale());
            System.out.println("Exception Upload Image: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new FileResponseMessage(message));
        }
    }

    @PostMapping("/upload-file-for-ads")
    public ResponseEntity<?> uploadImagesForAds(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId,
            @RequestParam("unitId") Long unitId,
            @RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader) {

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

        List<ImageUploadResponse> responses = new ArrayList<>();

        try {
            storageService.storeForAds(file, userId, unitId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(201, messageSource.getMessage("Successful_Upload.message", null, LocaleContextHolder.getLocale()) + responses));
        } catch (IOException e) {
            System.out.println("Exception Upload Image: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body( messageSource.getMessage("Failed_Upload.message", null, LocaleContextHolder.getLocale()));
        }
    }

    @PostMapping("/upload-file-for-pds")
    public ResponseEntity<?> uploadImagesForPdf(@RequestParam("file") MultipartFile file,
                                                @RequestParam("userId") Long userId,
                                                @RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader) {

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

        List<ImageUploadResponse> responses = new ArrayList<>();

        try {
            storageService.storeForPdf(file, userId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(201, messageSource.getMessage("Successful_Upload.message", null, LocaleContextHolder.getLocale()) + responses));
        } catch (IOException e) {
            System.out.println("Exception Upload Image: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body( messageSource.getMessage("Failed_Upload.message", null, LocaleContextHolder.getLocale()));
        }
    }


    @PostMapping("/upload-file-for-profile")
        public ResponseEntity<?> uploadImagesForProfile(
                @RequestParam("file") MultipartFile file,
                @RequestParam("userId") Long userId,
                @RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader) {

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

        List<ImageUploadResponse> responses = new ArrayList<>();

        try {
            storageService.storeForProfile(file, userId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(201, messageSource.getMessage("Successful_Upload.message", null, LocaleContextHolder.getLocale()) + responses));
        } catch (IOException e) {
            System.out.println("Exception Upload Image: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body( messageSource.getMessage("Failed_Upload.message", null, LocaleContextHolder.getLocale()));
        }
    }

    @PostMapping("/set-file-for-unit")
    public ResponseEntity<String> updateFileForUnit(
            @RequestParam("unitId") Long unitId,
            @RequestParam("userId") Long userId,
            @RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader) {

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

        StatusUnit statusUnit = statusUnitRepository.findById(1L).orElse(null);

        Unit unit = unitService.getUnitById(unitId);
        unit.setStatusUnit(statusUnit);
        unitService.saveUnit(unit);
        storageService.setFileForUnit(unitId, userId);
        return new ResponseEntity<>( messageSource.getMessage("Image_Updated.message", null, LocaleContextHolder.getLocale()) + unitId, HttpStatus.OK);
    }


    @PostMapping("/set-file-for-ads")
    public ResponseEntity<String> updateFileForAds(
            @RequestParam("adsId") Long adsId,
            @RequestParam("userId") Long userId,
            @RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader){

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

        storageService.setFileForAds(adsId, userId);
        return new ResponseEntity<>( messageSource.getMessage("Image_Updated.message", null, LocaleContextHolder.getLocale()) + adsId, HttpStatus.OK);
    }

    @GetMapping("/files-for-unit/{id}")
    public ResponseEntity<byte[]> getFileForUnit(@PathVariable String id) {
        FileForUnit fileForUnit = storageService.getFileForUnit(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileForUnit.getName() + "\"")
                .body(fileForUnit.getData());
    }

    @GetMapping("/file-for-ads/{id}")
    public ResponseEntity<byte[]> getFileForAds(@PathVariable String id) {
        FileForAds fileForAds = storageService.getFileForAds(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileForAds.getName() + "\"")
                .body(fileForAds.getData());
    }

    @GetMapping("/file-for-pdf/{id}")
    public ResponseEntity<byte[]> getFileForPdf(@PathVariable String id) {
        FileForPdf fileForPdf = storageService.getFileForPdf(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileForPdf.getName() + "\"")
                .body(fileForPdf.getData());
    }

    @GetMapping("/file-for-profile/{id}")
    public ResponseEntity<byte[]> getFileForProfile(@PathVariable String id) {
        FileForProfile fileForPdf = storageService.getFileForProfile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileForPdf.getName() + "\"")
                .body(fileForPdf.getData());
    }

    @Transactional
    @DeleteMapping("/delete-file-for-unit")
    public ResponseEntity<String> deleteFileForUnit(
            @RequestParam(required = false) Long unitId,
            @RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader){

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

        if (unitId != null) {
            storageService.deleteFilesForUnit(unitId);
        }

        return ResponseEntity.ok( messageSource.getMessage("Delete_Image.message", null, LocaleContextHolder.getLocale()));
    }

    @Transactional
    @DeleteMapping("/delete-Image-Data-For-Ads")
    public ResponseEntity<String> deleteImageDataForAds(
            @RequestParam(required = false) Long adsId,
            @RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader){

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

        if (adsId != null) {
            storageService.deleteFileForAds(adsId);
        }

        return ResponseEntity.ok( messageSource.getMessage("Delete_Image.message", null, LocaleContextHolder.getLocale()));
    }

}
