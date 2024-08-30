package com.AlTaraf.Booking.Service.File;

import com.AlTaraf.Booking.Entity.Ads.Ads;
import com.AlTaraf.Booking.Entity.File.FileForAds;
import com.AlTaraf.Booking.Entity.File.FileForPdf;
import com.AlTaraf.Booking.Entity.File.FileForProfile;
import com.AlTaraf.Booking.Entity.File.FileForUnit;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Repository.Ads.AdsRepository;
import com.AlTaraf.Booking.Repository.Ads.PackageAdsRepository;
import com.AlTaraf.Booking.Repository.File.FileForAdsRepository;
import com.AlTaraf.Booking.Repository.File.FileForPdfRepository;
import com.AlTaraf.Booking.Repository.File.FileForProfileRepository;
import com.AlTaraf.Booking.Repository.File.FileForUnitRepository;
import com.AlTaraf.Booking.Repository.unit.UnitRepository;
import com.AlTaraf.Booking.Repository.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;

@Service
public class FileStorageServiceImpl implements FileStorageService{

    @Autowired
    FileForUnitRepository fileForUnitRepository;

    @Autowired
    FileForAdsRepository fileForAdsRepository;

    @Autowired
    FileForPdfRepository fileForPdfRepository;

    @Autowired
    FileForProfileRepository fileForProfileRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UnitRepository unitRepository;

    @Autowired
    AdsRepository adsRepository;

    @Override
    public void storeForUnit(MultipartFile file, Long userId, MultipartFile video) throws IOException {

        String fileNameImage = StringUtils.cleanPath(file.getOriginalFilename());
        String fileNameVideo = StringUtils.cleanPath(video.getOriginalFilename());

        FileForUnit fileForUnitImage = new FileForUnit(fileNameImage, file.getContentType(), file.getBytes());
        FileForUnit fileForUnitVideo = new FileForUnit(fileNameVideo, video.getContentType(), video.getBytes());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        fileForUnitImage.setUser(user);
        fileForUnitVideo.setUser(user);

         fileForUnitRepository.save(fileForUnitImage);
         fileForUnitRepository.save(fileForUnitVideo);

        String fileDownloadImageUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .scheme("https") // Set the scheme to HTTPS
                .path("/files-for-unit/")
                .path(fileForUnitImage.getId())
                .toUriString();

        String fileDownloadVideoUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .scheme("https") // Set the scheme to HTTPS
                .path("/files-for-unit/")
                .path(fileForUnitVideo.getId())
                .toUriString();


        fileForUnitVideo.setFileVideoUrl(fileDownloadVideoUri);


        fileForUnitImage.setFileImageUrl(fileDownloadImageUri);

        fileForUnitRepository.save(fileForUnitImage);
        fileForUnitRepository.save(fileForUnitVideo);

    }

    @Override
    public void storeForUnit(Long userId, MultipartFile video) throws IOException {

        String fileNameVideo = StringUtils.cleanPath(video.getOriginalFilename());
        FileForUnit fileForUnitVideo = new FileForUnit(fileNameVideo, video.getContentType(), video.getBytes());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        fileForUnitVideo.setUser(user);
        fileForUnitRepository.save(fileForUnitVideo);

        String fileDownloadVideoUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .scheme("https") // Set the scheme to HTTPS
                .path("/files-for-unit/")
                .path(fileForUnitVideo.getId())
                .toUriString();


        fileForUnitVideo.setFileVideoUrl(fileDownloadVideoUri);

        fileForUnitRepository.save(fileForUnitVideo);
    }

    @Override
    public void storeForUnit( MultipartFile file, Long userId) throws IOException {

        String fileNameImage = StringUtils.cleanPath(file.getOriginalFilename());
        FileForUnit fileForUnitImage = new FileForUnit(fileNameImage, file.getContentType(), file.getBytes());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        fileForUnitImage.setUser(user);

        fileForUnitRepository.save(fileForUnitImage);

        String fileDownloadImageUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .scheme("https") // Set the scheme to HTTPS
                .path("/files-for-unit/")
                .path(fileForUnitImage.getId())
                .toUriString();


        fileForUnitImage.setFileImageUrl(fileDownloadImageUri);

        fileForUnitRepository.save(fileForUnitImage);
    }

    @Override
    public FileForAds storeForAds(MultipartFile file, Long userId, Long unitId) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileForAds fileForAds = new FileForAds(fileName, file.getContentType(), file.getBytes());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new RuntimeException("Unit not found with ID: " + unitId));
        fileForAds.setUnit(unit);
        fileForAds.setUser(user);
        fileForAdsRepository.save(fileForAds);

        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .scheme("https") // Set the scheme to HTTPS
                .path("/file-for-ads/")
                .path(fileForAds.getId())
                .toUriString();

        fileForAds.setFileDownloadUri(fileDownloadUri);

        return  fileForAdsRepository.save(fileForAds);
    }

    @Override
    public FileForPdf storeForPdf(MultipartFile file, Long userId) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileForPdf fileForPdf = new FileForPdf(fileName, file.getContentType(), file.getBytes());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        fileForPdf.setUser(user);
        fileForPdfRepository.save(fileForPdf);

        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .scheme("https") // Set the scheme to HTTPS
                .path("/file-for-pdf/")
                .path(fileForPdf.getId())
                .toUriString();

        fileForPdf.setFileDownloadUri(fileDownloadUri);

        return  fileForPdfRepository.save(fileForPdf);
    }

    @Override
    public FileForProfile storeForProfile(MultipartFile file, Long userId) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileForProfile fileForProfile = new FileForProfile(fileName, file.getContentType(), file.getBytes());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        user.setFileForProfile(fileForProfile);
        fileForProfileRepository.save(fileForProfile);

        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .scheme("https") // Set the scheme to HTTPS
                .path("/file-for-profile/")
                .path(fileForProfile.getId())
                .toUriString();

        fileForProfile.setFileDownloadUri(fileDownloadUri);

        return  fileForProfileRepository.save(fileForProfile);
    }

    @Override
    public void setFileForUnit(Long unitId, Long userId) {
        Unit unit = unitRepository.findById(unitId).orElse(null);

        if (unit != null) {
            List<FileForUnit> fileForUnitList = fileForUnitRepository.findByUserIdAndUnitIsNull(userId);

            for (FileForUnit fileForUnit : fileForUnitList) {
                fileForUnit.setUnit(unit);
                fileForUnitRepository.save(fileForUnit);
            }
        }
        else {
            throw new EntityNotFoundException("Unit not found with ID: " + unitId);
        }
    }

    @Override
    public void setFileForAds( Long adsId, Long userId) {

        Ads ads = adsRepository.findById(adsId).orElse(null);

        if (ads != null) {
            List<FileForAds> fileForAdsList = fileForAdsRepository.findByUserIdAndAdsIsNull(userId);

            for (FileForAds fileForAds : fileForAdsList) {
                fileForAds.setAds(ads);
                fileForAdsRepository.save(fileForAds);
            }
        }
        else {
            throw new EntityNotFoundException("Unit not found with ID: " + adsId);
        }
    }

    @Override
    public void deleteFilesForUnit(Long unitId) {
        fileForUnitRepository.deleteByUnitId(unitId);
    }

    @Override
    public void deleteFileForAds(Long adsId) {
        fileForAdsRepository.deleteByAdsId(adsId);
    }

    @Override
    public FileForUnit getFileForUnit(String id) {
        return fileForUnitRepository.findById(id).get();
    }

    @Override
    public FileForAds getFileForAds(String id) {
        return fileForAdsRepository.findById(id).get();
    }

    @Override
    public FileForPdf getFileForPdf(String id) {
        return fileForPdfRepository.findById(id).get();
    }

    @Override
    public FileForProfile getFileForProfile(String id) {
        return fileForProfileRepository.findById(id).get();
    }
}
