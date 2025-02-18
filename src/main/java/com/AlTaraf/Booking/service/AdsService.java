package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.database.entity.Ads;
import com.AlTaraf.Booking.database.entity.PackageAds;
import com.AlTaraf.Booking.database.entity.StatusUnit;
import com.AlTaraf.Booking.database.entity.User;
import com.AlTaraf.Booking.database.repository.AdsRepository;
import com.AlTaraf.Booking.database.repository.PackageAdsRepository;
import com.AlTaraf.Booking.database.repository.StatusRepository;
import com.AlTaraf.Booking.database.repository.UserRepository;
import com.AlTaraf.Booking.rest.dto.CounterAds;
import com.AlTaraf.Booking.rest.dto.PushNotificationRequest;
import com.AlTaraf.Booking.rest.dto.adsForSliderResponseDto;
import com.AlTaraf.Booking.rest.mapper.SliderMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class AdsService {

    @Autowired
    PackageAdsRepository packageAdsRepository;

    @Autowired
    AdsRepository adsRepository;

    @Autowired
    SliderMapper sliderMapper;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    NotificationService notificationService;

    @Autowired
    MessageSource messageSource;


    public List<PackageAds> getAllPackageAds() {
        return packageAdsRepository.findAll();
    }

    public Ads createAds(Ads ads) {
        try {
            return adsRepository.save(ads);
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            throw e;
        }
    }

    public List<Ads> getAdsForUserAndStatus(Long userId, Long  statusUnitId, Pageable pageable) {
        return adsRepository.findAllAdsByUserIdAndStatusUnitId(userId, statusUnitId, pageable);
    }
    public void deleteAds(Long id) {
        adsRepository.deleteById(id);
    }

    public List<adsForSliderResponseDto> getAdsByAcceptedStatus() {
        List<Ads> adsList = adsRepository.findByStatusUnitName("ACCEPTED");
        return sliderMapper.toSliderDtoList(adsList);
    }

    public void updateStatusForAds(Long adsId, Long statusUnitId) throws IOException, InterruptedException {
        Ads ads = adsRepository.findById(adsId)
                .orElseThrow(() -> new EntityNotFoundException("Ads not found with id: " + adsId));

        StatusUnit statusUnit = statusRepository.findById(statusUnitId)
                .orElseThrow(() -> new EntityNotFoundException("StatusUnit not found with id: " + statusUnitId));

        User user = ads.getUser();

        Integer numberAds = user.getNumberAds();

        if (numberAds > 0) {
            numberAds--;
            user.setNumberAds(numberAds);
        }

        userRepository.save(user);

        if (numberAds == 0) {
            user.setNumberAds(null);
            user.setPackageAds(null);
            userRepository.save(user);
        }

        ads.setStatusUnit(statusUnit);
        ads.setDateAds(LocalDate.now().plusDays(30));
        adsRepository.save(ads);

        if ( statusUnitId == 2) {
            PushNotificationRequest notificationRequest = new PushNotificationRequest(messageSource.getMessage("notification_title.message", null, LocaleContextHolder.getLocale()),messageSource.getMessage("notification_body_accepted_ads.message", null, LocaleContextHolder.getLocale()) + " " + ads.getUnit().getNameUnit(), ads.getUser().getId(),null, null, adsId);
            notificationService.processNotification(notificationRequest);
        } else if ( statusUnitId == 3) {
            PushNotificationRequest notificationRequest = new PushNotificationRequest(messageSource.getMessage("notification_title.message", null, LocaleContextHolder.getLocale()),messageSource.getMessage("notification_body_rejected_ads.message", null, LocaleContextHolder.getLocale()) + " " + ads.getUnit().getNameUnit(), ads.getUser().getId(), null, null, adsId);
            notificationService.processNotification(notificationRequest);
        }
    }

    public Ads findByUnitId(Long unitId) {
        return adsRepository.findByUnitId(unitId);
    }

    public CounterAds getCountAds() {
        CounterAds counterAds = new CounterAds();

        counterAds.setCounterAllAds(adsRepository.countAllAds());
        counterAds.setCounterAcceptedAds(adsRepository.counterAcceptedAds());
        counterAds.setCounterRejectedAds(adsRepository.counterRejectedAds());

        return counterAds;
    }

    public Ads getByUserIdAndUnitId(Long userId, Long unitId) {
        return adsRepository.findByUserIdAndUnitId(userId, unitId);
    }

    public Integer getAdsCountByUserId(Long userId) {
        return adsRepository.countAdsByUserId(userId);
    }
}
