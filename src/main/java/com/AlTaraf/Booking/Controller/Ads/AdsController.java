package com.AlTaraf.Booking.Controller.Ads;

import com.AlTaraf.Booking.Dto.Unit.UnitDtoFavorite;
import com.AlTaraf.Booking.Dto.ads.AdsResponse;
import com.AlTaraf.Booking.Entity.Ads.Ads;
import com.AlTaraf.Booking.Entity.Ads.PackageAds;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.Wallet.Wallet;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Entity.unit.statusUnit.StatusUnit;
import com.AlTaraf.Booking.Exception.InsufficientFundsException;
import com.AlTaraf.Booking.Mapper.Ads.AdsMapper;
import com.AlTaraf.Booking.Mapper.Ads.AdsStatusMapper;
import com.AlTaraf.Booking.Mapper.Unit.UnitFavoriteMapper;
import com.AlTaraf.Booking.Mapper.Unit.UnitGeneralResponseMapper;
import com.AlTaraf.Booking.Payload.request.Ads.AdsRequestDto;
import com.AlTaraf.Booking.Payload.request.Ads.AdsResponseStatusDto;
import com.AlTaraf.Booking.Payload.response.Ads.adsForSliderResponseDto;
import com.AlTaraf.Booking.Payload.response.ApiResponse;
import com.AlTaraf.Booking.Repository.Ads.AdsRepository;
import com.AlTaraf.Booking.Repository.Ads.PackageAdsRepository;
import com.AlTaraf.Booking.Repository.Wallet.WalletRepository;
import com.AlTaraf.Booking.Repository.unit.statusUnit.StatusRepository;
import com.AlTaraf.Booking.Repository.user.UserRepository;
import com.AlTaraf.Booking.Service.Ads.AdsService;
import com.AlTaraf.Booking.Service.notification.NotificationService;
import com.AlTaraf.Booking.Service.unit.UnitService;
import com.AlTaraf.Booking.Service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ads")
public class AdsController {

    @Autowired
    AdsService adsService;

    @Autowired
    UnitFavoriteMapper unitFavoriteMapper;

    @Autowired
    UnitService unitService;

    @Autowired
    AdsMapper adsMapper;

    @Autowired
    AdsStatusMapper adsStatusMapper;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageSource messageSource;

    @Autowired
    AdsRepository adsRepository;

    @Autowired
    StatusRepository statusUnitRepository;

    @Autowired
    PackageAdsRepository packageAdsRepository;

    @Autowired
    WalletRepository walletRepository;

    @GetMapping("/package-ads")
    public ResponseEntity<?> getAllPackageAds(@RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader) {

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

        try {
            List<PackageAds> allPackageAds = adsService.getAllPackageAds();
            return new ResponseEntity<>(allPackageAds, HttpStatus.OK);
        } catch (Exception e) {
            // Handle the exception here
            return  ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, messageSource.getMessage("no_content.message", null, LocaleContextHolder.getLocale())));
        }
    }


    @GetMapping("units/by-user-id/{userId}")
    public ResponseEntity<?> getUnitsByUserId(@PathVariable Long userId,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size,
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

        Page<Unit> unitsPage = unitService.getUnitsByUserId(userId, PageRequest.of(page, size));

        if (unitsPage.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, messageSource.getMessage("no_unit_for_user.message: " + userId, null, LocaleContextHolder.getLocale())+ userId));
        } else {
            List<UnitDtoFavorite> unitGeneralResponseDtos = unitsPage.getContent().stream()
                    .map(unitFavoriteMapper::toUnitFavoriteDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(unitGeneralResponseDtos);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAds(@RequestBody AdsRequestDto adsRequestDto,
                                       @RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader) {


            Locale locale = LocaleContextHolder.getLocale();
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

            User user = userRepository.findByUserId(adsRequestDto.getUserId());
            PackageAds packageAds = packageAdsRepository.findById(0L).orElse(null);

            Ads existAds = null;
            existAds = adsService.getByUserIdAndUnitId(adsRequestDto.getUserId(), adsRequestDto.getUnitId());


            if (existAds != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageSource.getMessage("exist_ads.message", null, LocaleContextHolder.getLocale()));
            }

            int numberAds = user.getNumberAds();
            System.out.println("numberAds: " + numberAds);

        if (numberAds == 0) {
            user.setPackageAds(packageAds);
            userRepository.save(user);
            System.out.println("Number Ads is Zero");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageSource.getMessage("package_ads_null.message", null, LocaleContextHolder.getLocale()));
        }

            if (numberAds > 0) {
                numberAds--;
                user.setNumberAds(numberAds);
            }

            if (numberAds == 0) {
                user.setPackageAds(packageAds);
            }

            userRepository.save(user);

//            PushNotificationRequest notificationRequest = new PushNotificationRequest(messageSource.getMessage("notification_title.message", null, LocaleContextHolder.getLocale()),messageSource.getMessage("notification_body_ads.message", null, LocaleContextHolder.getLocale()) +  " لوحدة " + existAds.getUnit().getNameUnit(),user.getId());
//            notificationService.processNotification(notificationRequest);
            Ads createdAds = adsService.createAds(adsMapper.toEntity(adsRequestDto));
            Long createdAdsId = createdAds.getId();
            AdsResponse adsResponse = new AdsResponse(createdAdsId, messageSource.getMessage("ads_created.message" , null, LocaleContextHolder.getLocale()));
            return ResponseEntity.status(HttpStatus.OK).body(adsResponse);

    }

    @GetMapping("/status-unit/ads")
    public ResponseEntity<?> getAdsByUserAndStatus(
            @RequestParam(name = "userId") Long userId,
            @RequestParam(name = "statusUnitId") Long statusUnitId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
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

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());


        List<Ads> ads = adsService.getAdsForUserAndStatus(userId, statusUnitId, pageable);

        if (!ads.isEmpty()) {
            List<AdsResponseStatusDto> adsDtoList = adsStatusMapper.toAdsDtoList(ads);
//            List<adsForSliderResponseDto> ads = adsService.getAdsByAcceptedStatus();
            return new ResponseEntity<>(adsDtoList, HttpStatus.OK);
        } else {
            ApiResponse response = new ApiResponse(200, messageSource.getMessage("no_content.message", null, LocaleContextHolder.getLocale()));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @DeleteMapping("delete/ads/{id}")
    public ResponseEntity<?> deleteAds(@PathVariable Long id,
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

        Ads ads = adsRepository.findById(id).orElse(null);

        StatusUnit statusUnit = statusUnitRepository.findById(4L).orElse(null);

        ads.setStatusUnit(statusUnit);

        adsRepository.save(ads);


        ApiResponse response = new ApiResponse(200, messageSource.getMessage("ads_deleted.message", null, LocaleContextHolder.getLocale()));
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }


    @GetMapping("/accepted/status")
    public ResponseEntity<?> getAdsByAcceptedStatus(@RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader) {
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

            List<adsForSliderResponseDto> ads = adsService.getAdsByAcceptedStatus();
            return ResponseEntity.ok(ads);
        } catch (Exception e) {
            System.out.println("Exception Accepted Ads: " + e);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, messageSource.getMessage("no_content.message", null, LocaleContextHolder.getLocale())));
        }
    }

//    @PostMapping("/{userId}/package-ads/{packageAdsId}")
//    public ResponseEntity<?> setPackageAdsForUser(@PathVariable Long userId, @PathVariable Long packageAdsId) {
//        try {
//            User user = userService.setPackageAdsForUser(userId, packageAdsId);
//
//            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200,messageSource.getMessage("successful_package_ads.message", null, LocaleContextHolder.getLocale()) + " " + user.getWallet()));
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(400,messageSource.getMessage("fail_package_ads.message", null, LocaleContextHolder.getLocale())));
//        } catch (InsufficientFundsException e) {
//            return ResponseEntity.badRequest().body(messageSource.getMessage("fail_package_ads_wallet.message", null, LocaleContextHolder.getLocale()));
//        }
//    }

    @PostMapping("/{userId}/package-ads/{packageAdsId}")
    public ResponseEntity<?> setPackageAdsForUser(@PathVariable Long userId,
                                                  @PathVariable Long packageAdsId,
                                                  @RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader) throws InsufficientFundsException {


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

            User user = userRepository.findByUserId(userId);

            PackageAds packageAds = packageAdsRepository.findById(packageAdsId).orElse(null);

            if (user.getPackageAds() != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(402,messageSource.getMessage("fail_package_ads_exist.message", null, LocaleContextHolder.getLocale()) + ": " + user.getNumberAds()));
            }

            if(user.getWallet() < packageAds.getPrice()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageSource.getMessage("fail_package_ads_wallet.message", null, LocaleContextHolder.getLocale()));
            }

             user = userService.setPackageAdsForUser(userId, packageAdsId);

        Wallet wallet = new Wallet("اشتراك في باقة اعلان", "Subscribe Package Ads", packageAds.getPrice(),user, "", packageAds.getArabicName(), "", false);
        walletRepository.save(wallet);

            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200,messageSource.getMessage("successful_package_ads.message", null, LocaleContextHolder.getLocale()) + " " + user.getWallet()));
        }

}
