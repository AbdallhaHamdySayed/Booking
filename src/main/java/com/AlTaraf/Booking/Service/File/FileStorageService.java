package com.AlTaraf.Booking.Service.File;

import com.AlTaraf.Booking.Entity.File.FileForAds;
import com.AlTaraf.Booking.Entity.File.FileForPdf;
import com.AlTaraf.Booking.Entity.File.FileForProfile;
import com.AlTaraf.Booking.Entity.File.FileForUnit;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {
    void storeForUnit(MultipartFile file, Long userId, MultipartFile video) throws IOException;
    void storeForUnit(Long userId, MultipartFile video) throws IOException;
    void storeForUnit(MultipartFile file, Long userId) throws IOException;
    FileForAds storeForAds(MultipartFile file, Long userId, Long unitId) throws IOException;

    FileForPdf storeForPdf(MultipartFile file, Long userId) throws IOException;
    FileForProfile storeForProfile(MultipartFile file, Long userId) throws IOException;

    void setFileForUnit( Long unitId, Long userId);

    void setFileForAds( Long adsId, Long userId);

    void deleteFilesForUnit(Long unitId);
    void deleteFileForAds(Long adsId);
    FileForUnit getFileForUnit(String id);
    FileForAds getFileForAds(String id);
    FileForPdf getFileForPdf(String id);
    FileForProfile getFileForProfile(String id);
}
