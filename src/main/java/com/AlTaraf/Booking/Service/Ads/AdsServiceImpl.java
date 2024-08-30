package com.AlTaraf.Booking.Service.Ads;

import com.AlTaraf.Booking.Dto.Notifications.PushNotificationRequest;
import com.AlTaraf.Booking.Entity.Ads.Ads;
import com.AlTaraf.Booking.Entity.Ads.PackageAds;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.unit.statusUnit.StatusUnit;
import com.AlTaraf.Booking.Mapper.Ads.SliderMapper;
import com.AlTaraf.Booking.Payload.response.Ads.adsForSliderResponseDto;
import com.AlTaraf.Booking.Payload.response.CounterAds;
import com.AlTaraf.Booking.Repository.Ads.AdsRepository;
import com.AlTaraf.Booking.Repository.Ads.PackageAdsRepository;
import com.AlTaraf.Booking.Repository.unit.UnitRepository;
import com.AlTaraf.Booking.Repository.unit.statusUnit.StatusRepository;
import com.AlTaraf.Booking.Repository.user.UserRepository;
import com.AlTaraf.Booking.Service.notification.NotificationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class AdsServiceImpl implements AdsService {

    @Autowired
    private PackageAdsRepository packageAdsRepository;

    @Autowired
    private AdsRepository adsRepository;

    @Autowired
    private SliderMapper sliderMapper;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    UnitRepository unitRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    NotificationService notificationService;

    @Autowired
    MessageSource messageSource;

    @Override
    public List<adsForSliderResponseDto> getAdsByStatusUnitId(Long statusUnitId) {
        List<Ads> adsList = adsRepository.findByStatusUnitId(statusUnitId); // Fetch ads by statusUnitId
        return sliderMapper.toSliderDtoList(adsList); // Map Ads entities to adsForSliderResponseDto objects    }
    }

    public List<PackageAds> getAllPackageAds() {
        return packageAdsRepository.findAll();
    }

    @Override
    public Ads createAds(Ads ads) {
        try {
            return adsRepository.save(ads);
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Ads> getAdsForUserAndStatus(Long userId, Long  statusUnitId, Pageable pageable) {
        // Retrieve a List of Ads for the given USER_ID and StatusUnit name
        return adsRepository.findAllAdsByUserIdAndStatusUnitId(userId, statusUnitId, pageable);
    }

    @Override
    public void deleteAds(Long id) {
        adsRepository.deleteById(id);
    }

    @Override
    public List<adsForSliderResponseDto> getAdsByAcceptedStatus() {
        List<Ads> adsList = adsRepository.findByStatusUnitName("ACCEPTED");
        return sliderMapper.toSliderDtoList(adsList);
    }

    @Override
    public void updateStatusForAds(Long adsId, Long statusUnitId) throws IOException, InterruptedException {
        Ads ads = adsRepository.findById(adsId)
                .orElseThrow(() -> new EntityNotFoundException("Ads not found with id: " + adsId));

        StatusUnit statusUnit = statusRepository.findById(statusUnitId)
                .orElseThrow(() -> new EntityNotFoundException("StatusUnit not found with id: " + statusUnitId));

        User user = ads.getUser();
//        System.out.println("user: " + ads.getUser().getId());

//        System.out.println("number ads for user: " + user.getNumberAds());

        Integer numberAds = user.getNumberAds();
        numberAds--;
        user.setNumberAds(numberAds);
        userRepository.save(user);

        ads.setStatusUnit(statusUnit);
        adsRepository.save(ads);

        if ( statusUnitId == 2) {
//            PushNotificationRequest notificationRequest = new PushNotificationRequest("رسالة من الادمن","تم قبول طلب اضافة وحدتك",ads.getUser().getId());
            PushNotificationRequest notificationRequest = new PushNotificationRequest(messageSource.getMessage("notification_title.message", null, LocaleContextHolder.getLocale()),messageSource.getMessage("notification_body_accepted_ads.message", null, LocaleContextHolder.getLocale()) + " " + ads.getUnit().getNameUnit(), ads.getUser().getId());
            notificationService.processNotification(notificationRequest);
        } else if ( statusUnitId == 3) {
            PushNotificationRequest notificationRequest = new PushNotificationRequest(messageSource.getMessage("notification_title.message", null, LocaleContextHolder.getLocale()),messageSource.getMessage("notification_body_rejected_ads.message", null, LocaleContextHolder.getLocale()) + " " + ads.getUnit().getNameUnit(), ads.getUser().getId());
            notificationService.processNotification(notificationRequest);
        }
    }


    @Override
    public Ads findByUnitId(Long unitId) {
        return adsRepository.findByUnitId(unitId);
    }

    @Override
    public Page<Ads> findAllByStatusUnitId(Long statusUnitId, Pageable pageable) {
        return adsRepository.findAllByStatusUnitId(statusUnitId, pageable);
    }

    @Override
    public CounterAds getCountAds() {
//        CounterAds counterAds = counterAdsRepository.findById(1L).orElse(null);
        CounterAds counterAds = new CounterAds();

        counterAds.setCounterAllAds(adsRepository.countAllAds());
        counterAds.setCounterAcceptedAds(adsRepository.counterAcceptedAds());
        counterAds.setCounterRejectedAds(adsRepository.counterRejectedAds());

//        counterAdsRepository.save(counterAds);

        return counterAds;
    }

    @Override
    public Ads getByUserIdAndUnitId(Long userId, Long unitId) {
        return adsRepository.findByUserIdAndUnitId(userId, unitId);
    }
}

