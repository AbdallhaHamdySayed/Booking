package com.AlTaraf.Booking.rest.controller;

import com.AlTaraf.Booking.database.entity.*;
import com.AlTaraf.Booking.database.repository.*;
import com.AlTaraf.Booking.rest.dto.*;
import com.AlTaraf.Booking.rest.mapper.*;
import com.AlTaraf.Booking.service.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    TechnicalSupportService technicalSupportService;

    @Autowired
    TechnicalSupportUnitsService technicalSupportUnitsService;

    @Autowired
    UserService userService;

    @Autowired
    UnitService unitService;

    @Autowired
    UnitGeneralResponseMapper unitGeneralResponseMapper;

    @Autowired
    AdsService adsService;

    @Autowired
    ReservationService reservationService;

    @Autowired
    AdsRepository adsRepository;

    @Autowired
    UnitDashboardMapper unitDashboard;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDashboardMapper userDashboardMapper;

    @Autowired
    PackageAdsRepository packageAdsRepository;

    @Autowired
    TechnicalSupportRepository technicalSupportRepository;

    @Autowired
    TechnicalSupportUnitRepository technicalSupportUnitRepository;

    @Autowired
    TechnicalSupportMapper technicalSupportMapper;

    @Autowired
    TechnicalSupportUnitsMapper technicalSupportUnitsMapper;

    @Autowired
    AdsStatusMapper adsStatusMapper;

    @Autowired
    NotificationMapper notificationMapper;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    NotificationService notificationService;

    @Autowired
    TransactionService transactionService;


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/technical-support-get-all")
    public Page<TechnicalSupportResponse> getAllTechnicalSupport(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "5") int size,
                                                                 @RequestParam(required = false) Boolean seen) {
        Page<TechnicalSupport> technicalSupportPage;
        if (seen != null) {
            technicalSupportPage = technicalSupportService.getTechnicalSupportBySeen(seen, PageRequest.of(page, size));
        } else {
            technicalSupportPage = technicalSupportService.getAllTechnicalSupport(PageRequest.of(page, size));
        }
        List<TechnicalSupportResponse> technicalSupportResponseList = technicalSupportPage.getContent().stream()
                .map(TechnicalSupportMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(technicalSupportResponseList, PageRequest.of(page, size), technicalSupportPage.getTotalElements());
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/technical-support-unit-get-all")
    public Page<TechnicalSupportUnitsResponse> getAllTechnicalSupportUnit(@RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "5") int size) {
        Page<TechnicalSupportForUnits> technicalSupportPage = technicalSupportUnitsService.getAllTechnicalSupport(PageRequest.of(page, size));
        List<TechnicalSupportUnitsResponse> technicalSupportResponseList = technicalSupportPage.getContent().stream()
                .map(TechnicalSupportUnitsMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(technicalSupportResponseList, PageRequest.of(page, size), technicalSupportPage.getTotalElements());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("technical-support/{id}/mark-as-seen")
    public ResponseEntity<?> markAsSeen(@PathVariable Long id) {
        Optional<TechnicalSupport> optionalTechnicalSupport = technicalSupportRepository.findById(id);
        if (optionalTechnicalSupport.isPresent()) {
            TechnicalSupport technicalSupport = optionalTechnicalSupport.get();
            technicalSupport.setSeen(true);
            technicalSupportRepository.save(technicalSupport);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "Marked As seen"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(404, "Technical Support Id Not Found"));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("technical-support-unit/{id}/mark-as-seen")
    public ResponseEntity<?> markAsSeenUnit(@PathVariable Long id) {
        Optional<TechnicalSupportForUnits> optionalTechnicalSupport = technicalSupportUnitRepository.findById(id);
        if (optionalTechnicalSupport.isPresent()) {
            TechnicalSupportForUnits technicalSupportForUnits = optionalTechnicalSupport.get();
            technicalSupportForUnits.setSeen(true);
            technicalSupportUnitRepository.save(technicalSupportForUnits);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "Marked As seen"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(404, "Technical Support Unit Id Not Found"));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/technical-support/{id}")
    public ResponseEntity<TechnicalSupportResponse> getByIdTechnicalSupport(@PathVariable Long id) {
        Optional<TechnicalSupport> optionalTechnicalSupport = technicalSupportRepository.findById(id);
        return optionalTechnicalSupport
                .map(technicalSupport -> ResponseEntity.ok().body(technicalSupportMapper.toDto(technicalSupport)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/technical-support-units/{id}")
    public ResponseEntity<TechnicalSupportUnitsResponse> getByIdTechnicalSupportUnits(@PathVariable Long id) {
        Optional<TechnicalSupportForUnits> optionalTechnicalSupport = technicalSupportUnitRepository.findById(id);
        return optionalTechnicalSupport
                .map(technicalSupportForUnits -> ResponseEntity.ok().body(technicalSupportUnitsMapper.toDto(technicalSupportForUnits)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-units-by-accommodation-Type")
    public ResponseEntity<?> getUnitsByAccommodationType(
            @RequestParam Long accommodationTypeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {

        Page<UnitDashboard> units = unitService.getUnitsByAccommodationTypeNameDashboard(accommodationTypeId, page, size);

        if (!units.isEmpty()) {
            return ResponseEntity.ok(units);
        } else {
            ApiResponse response = new ApiResponse(204, "No Content for Units By Accommodation Type!");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-units-for-dashboard")
    public Page<UnitDashboard> getUnitsForDashboard(
            @RequestParam(required = false) String traderName,
            @RequestParam(required = false) String traderPhone,
            @RequestParam Long unitTypeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Page<Unit> unitsPage = Page.empty();

        if (traderName == null && traderPhone == null && unitTypeId != null) {
            unitsPage = unitService.getUnitsByUnitTypeIdForDashboard(unitTypeId, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));
        }

        else if (traderName != null && unitTypeId != null) {
            unitsPage = unitService.filterUnitsByUserNameAndTypeId(traderName, unitTypeId, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));
        }
        else if (traderName == null && unitTypeId != null && traderPhone != null) {
            unitsPage = unitService.filterUnitsByPhoneNumberAndTypeId(traderPhone, unitTypeId,PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));
        }
        return unitsPage.map(unit -> unitDashboard.toUnitDashboard(unit));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}/technical-support")
    public ResponseEntity<?> deleteTechnicalSupportById(@PathVariable Long id) {
        try {
            technicalSupportService.deleteTechnicalSupportById(id);
            return ResponseEntity.ok(new ApiResponse(200, "Technical support message deleted successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(500, "Failed to delete technical support message."));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")

    @DeleteMapping("/deleteAll/technical-support")
    public ResponseEntity<?> deleteAllTechnicalSupport() {
        try {
            technicalSupportService.deleteAllTechnicalSupport();
            return ResponseEntity.ok(new ApiResponse(200, "All technical support messages deleted successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(500, "Failed to delete all technical support messages."));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}/technical-support-units")
    public ResponseEntity<?> deleteTechnicalSupportUnitsById(@PathVariable Long id) {
        try {
            technicalSupportUnitsService.deleteTechnicalSupportById(id);
            return ResponseEntity.ok(new ApiResponse(200, "Technical support Unit message deleted successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(500, "Failed to delete technical support Unit message."));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/deleteAll/technical-support-units")
    public ResponseEntity<?> deleteAllTechnicalSupportUnits() {
        try {
            technicalSupportUnitsService.deleteAllTechnicalSupport();
            return ResponseEntity.ok(new ApiResponse(200, "All technical support Unit messages deleted successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(500, "Failed to delete all technical support Unit messages."));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("by-id-general/{id}")
    public ResponseEntity<?> getUnitById(@PathVariable Long id) {
        Unit unit = unitService.getUnitById(id);
        if (unit != null) {
            UnitGeneralResponseDto responseDto = unitGeneralResponseMapper.toResponseDto(unit);
            return ResponseEntity.ok(responseDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(404, "Not Found!"));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")

    @PutMapping("change/status/ads/{adsId}/{statusUnitId}")
    public ResponseEntity<?> updateStatusForAds(@PathVariable Long adsId, @PathVariable Long statusUnitId) {
        try {
            adsService.updateStatusForAds(adsId, statusUnitId);
            return ResponseEntity.ok("Status updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update status: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("change/status/units/{unitId}/{statusUnitId}")
    public ResponseEntity<?> updateStatusForUnits(@PathVariable Long unitId, @PathVariable Long statusUnitId) {
        try {
            unitService.updateStatusForUser(unitId, statusUnitId);
            return ResponseEntity.ok("Status updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update status: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    @DeleteMapping("delete/unit/{id}")
    public ResponseEntity<?> deleteUnit(@PathVariable Long id) {
        try {
            unitService.deleteUnitWithDependencies(id);
            ApiResponse response = new ApiResponse(200, "Unit deleted successfully!");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            // Log the exception or handle it in some way
            System.err.println("Error deleting unit: " + e.getMessage());
            ApiResponse response = new ApiResponse(500, "Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public void sendNotification(PushNotificationRequest request) throws IOException, InterruptedException {
        Notifications notification = notificationMapper.dtoToEntity(request);
        notificationRepository.save(notification);
        notificationService.sendPushMessage(request.getTitle(), request.getBody(), request.getUserId());
    }



    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{userId}/ban")
    public ResponseEntity<?> toggleBanStatus(@PathVariable Long userId) {

        PushNotificationRequest banTrue = new PushNotificationRequest();
        banTrue.setTitle("Notification.ban.title.message");
        banTrue.setBody("Notification.ban.body.message");
        banTrue.setUserId(userId);

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = optionalUser.get();
        if (user.getBan() == null) {
            user.setBan(true);
        } else {
            user.setBan(!user.getBan());
        }
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "Ban for User are Changed"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-user-all-or-byrole")
    public ResponseEntity<Page<?>> getUsersByRole(
            @RequestParam(required = false) ERole roleName,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String phone,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<User> usersPage;
        if (roleName != null && username != null && phone != null) {
            usersPage = userRepository.findAllByRolesNameAndUserNameAndPhone(roleName, username, phone, pageable);
        } else if (roleName != null && username == null && phone == null) {
            usersPage = userRepository.findAllByRolesName(roleName, pageable);
        } else if (roleName != null && username != null && phone == null) {
            usersPage = userRepository.findByUserNameAndRolesName(username, roleName, pageable);
        } else if (roleName == null && username == null && phone != null) {
            usersPage = userRepository.findAllByPhoneExcludingRoles(phone, pageable);
        }
        else if (roleName == null && phone == null && username != null ) {
            usersPage = userRepository.findByUserName(username, pageable);
        } else if (roleName != null && username == null && phone != null) {
            usersPage = userRepository.findAllByPhoneAndRolesName(phone, roleName, pageable);
        }
        else {
            usersPage = userRepository.findAllExclude(pageable);
        }
        Page<UserDashboard> userDashboardPage = usersPage.map(userDashboardMapper::toUserDashboard);

        return ResponseEntity.ok(userDashboardPage);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")

    @GetMapping("/users-of-dashboard")
    public ResponseEntity<Page<?>> usersOfDashboard(
            @RequestParam(required = false) ERole roleName,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String phone,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<User> usersPage;
        if (roleName != null && username != null && phone != null) {
            usersPage = userRepository.findAllByRolesNameAndUserNameAndPhone(roleName, username, phone, pageable);
        } else if (roleName != null && username == null && phone == null) {
            usersPage = userRepository.findAllByRolesName(roleName, pageable);
        } else if (roleName != null && username != null && phone == null) {
            usersPage = userRepository.findByUserNameAndRolesName(username, roleName, pageable);
        } else if (roleName == null && username == null && phone != null) {
            usersPage = userRepository.findAllByPhoneExcludingRolesDashboard(phone, pageable);
        }
        else if (roleName == null && phone == null && username != null ) {
            usersPage = userRepository.findByUserNameDashboard(username, pageable);
        } else if (roleName != null && username == null && phone != null) {
            usersPage = userRepository.findAllByPhoneAndRolesName(phone, roleName, pageable);
        }
        else {
            usersPage = userRepository.findAllExcludeDashboard(pageable);
        }
        Page<UserDashboard> userDashboardPage = usersPage.map(userDashboardMapper::toUserDashboard);

        return ResponseEntity.ok(userDashboardPage);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("edit-package-ads/{id}")
    public ResponseEntity<?> editPackageAds(@PathVariable Long id, @RequestBody PackageAdsEditDTO packageAdsEditDTO) {
        Optional<PackageAds> optionalPackageAds = packageAdsRepository.findById(id);
        if (optionalPackageAds.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        PackageAds packageAds = optionalPackageAds.get();
        if (packageAdsEditDTO.getArabicName() != null) {
            packageAds.setArabicName(packageAdsEditDTO.getArabicName());
        }
        if (packageAdsEditDTO.getPrice() != 0) {
            packageAds.setPrice(packageAdsEditDTO.getPrice());
        }
        if (packageAdsEditDTO.getNumberAds() != 0) {
            packageAds.setNumberAds(packageAdsEditDTO.getNumberAds());
        }

        packageAdsRepository.save(packageAds);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "Updated Package Ads successfully"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete-users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUserAndAssociatedEntities(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200,"Deleted User Successfully"));
        } catch (Exception e) {
            // Log the exception or handle it in some way
            System.err.println("Error deleting user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, "Error When Delete User"));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{userId}/warnings")
    public ResponseEntity<?> setWarnings(@PathVariable Long userId, @RequestBody List<Boolean> warnings) {
        try {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            User user = optionalUser.get();
            user.setWarnings(warnings);
            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "Warning Added Successfully"));
        } catch (Exception e) {
            e.printStackTrace(); // You can log the exception for debugging purposes
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Warning Not Added");
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("get-ads-for-dashboard")
    public ResponseEntity<?> getAllAdsByPageAndSize(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) Long statusId
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Ads> adsPage;
        if (statusId != null) {
            adsPage = adsRepository.findAllByStatusUnitId(statusId, pageable);
        } else {
            adsPage = adsRepository.findAll(pageable);
        }
        Page<AdsResponseStatusDto> adsResponseStatusDtos = adsPage.map(adsStatusMapper::toDto);
        return ResponseEntity.ok(adsResponseStatusDtos);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/set-commission-all-units")
    public ResponseEntity<String> setCommissionForAllUnits(@RequestParam Double commission) {
        unitService.setCommissionForAllUnits(commission);
        return ResponseEntity.ok("Commission set successfully for all units.");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/set-commission-all-reservations")
    public ResponseEntity<String> setCommissionForAllReservations(@RequestParam Double commission) {
        reservationService.setCommissionForAllReservations(commission);
        return ResponseEntity.ok("Commission set successfully for all reservations.");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("delete/ads/{id}")
    public ResponseEntity<?> deleteAds(@PathVariable Long id) {

        try {
            adsService.deleteAds(id);
            ApiResponse response = new ApiResponse(200, "ads_deleted.message");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(404, "not_found.message");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-counter-units")
    public ResponseEntity<CounterUnits> getCounterUnit() {
        CounterUnits counterUnits = unitService.getCounterForResidenciesUnits();
        return new ResponseEntity<>(counterUnits, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-counter-users")
    public ResponseEntity<CounterUser> getCounterUser() {
        CounterUser counterUser = userService.getCountUser();
        return new ResponseEntity<>(counterUser, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-counter-ads")
    public ResponseEntity<CounterAds> getCounterAds() {
        CounterAds counterAds = adsService.getCountAds();
        return new ResponseEntity<>(counterAds, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/Package-Ads")
    public ResponseEntity<?> getAllPackageAds() {
        try {
            List<PackageAds> allPackageAds = adsService.getAllPackageAds();
            return new ResponseEntity<>(allPackageAds, HttpStatus.OK);
        } catch (Exception e) {
            // Handle the exception here
            return  ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, "no_content.message"));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/transactions/details")
    public ResponseEntity<?> getAllTransactionDetails(
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) Long transactionId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<TransactionDetailsDto> transactionDetailsDtoPage;
        if (transactionId != null && phone != null){
            transactionDetailsDtoPage = transactionService.findByTransactionIdAndPhone(transactionId, phone, pageable);
        }
        else if (phone != null && transactionId == null) {
            transactionDetailsDtoPage = transactionService.findByPhone(phone, pageable);
        } else if (transactionId != null && phone == null) {
            transactionDetailsDtoPage = transactionService.findByTransactionId(transactionId, pageable);
        } else {
            transactionDetailsDtoPage = transactionService.getAllTransactionDetails(pageable);
        }

        return ResponseEntity.ok(transactionDetailsDtoPage);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/transactions/total")
    public ResponseEntity<List<TotalTransactions>> getAllTotalTransactions() {
        List<TotalTransactions> totalTransactions = transactionService.getAllTotalTransactions();
        return ResponseEntity.ok(totalTransactions);
    }
}
