package com.AlTaraf.Booking.Controller.Reservations;

import com.AlTaraf.Booking.Dto.Notifications.PushNotificationRequest;
import com.AlTaraf.Booking.Entity.Evaluation.Evaluation;
import com.AlTaraf.Booking.Entity.Reservation.Reservations;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Entity.unit.availableArea.RoomDetailsForAvailableArea;
import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomDetails;
import com.AlTaraf.Booking.Entity.unit.statusUnit.StatusUnit;
import com.AlTaraf.Booking.Mapper.Reservation.ReservationGetByIdMapper;
import com.AlTaraf.Booking.Mapper.Reservation.ReservationRequestMapper;
import com.AlTaraf.Booking.Mapper.Reservation.ReservationStatusMapper;
import com.AlTaraf.Booking.Payload.request.Reservation.ReservationRequestDto;
import com.AlTaraf.Booking.Payload.response.ApiResponse;
import com.AlTaraf.Booking.Payload.response.Reservation.ReservationResponseGetId;
import com.AlTaraf.Booking.Payload.response.Reservation.ReservationStatus;
import com.AlTaraf.Booking.Repository.Evaluation.EvaluationRepository;
import com.AlTaraf.Booking.Repository.Reservation.ReservationRepository;
import com.AlTaraf.Booking.Repository.unit.statusUnit.StatusRepository;
import com.AlTaraf.Booking.Service.Reservation.ReservationService;
import com.AlTaraf.Booking.Service.notification.NotificationService;
import com.AlTaraf.Booking.Service.unit.RoomDetails.RoomDetailsService;
import com.AlTaraf.Booking.Service.unit.RoomDetailsForAvailableArea.RoomDetailsForAvailableAreaService;
import com.AlTaraf.Booking.Service.unit.UnitService;
import com.AlTaraf.Booking.Service.unit.availableArea.AvailableAreaService;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @Autowired
    ReservationRequestMapper reservationRequestMapper;

    @Autowired
    ReservationGetByIdMapper reservationGetByIdMapper;

    @Autowired
    UnitService unitService;

    @Autowired
    private RoomDetailsService roomDetailsService;

    @Autowired
    private RoomDetailsForAvailableAreaService roomDetailsForAvailableAreaService;

    @Autowired
    private AvailableAreaService availableAreaService;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private ReservationStatusMapper reservationStatusMapper;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    NotificationService notificationService;

    @Autowired
    private MessageSource messageSource;

    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);


    @PostMapping("/Create-Reservation")
    public ResponseEntity<?> createReservation( @RequestBody ReservationRequestDto reservationRequestDto,
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

            // Convert UnitRequestDto to Unit
            Reservations reservationsToSave = reservationRequestMapper.toReservation(reservationRequestDto);

            if (reservationRequestDto.getRoomAvailableId() != null) {
                RoomDetails roomDetails = roomDetailsService.getRoomDetailsByUnitIdAndRoomAvailableId(reservationRequestDto.getUnitId(), reservationRequestDto.getRoomAvailableId());
                System.out.println(roomDetails.getId() + "price: " + roomDetails.getNewPrice());
                if (roomDetails != null) {
                    // Update the price based on room details
                    reservationsToSave.setPrice(roomDetails.getNewPrice());
                }
            }


            else if (reservationRequestDto.getAvailableAreaId() != null) {
                RoomDetailsForAvailableArea roomDetailsForAvailableArea = roomDetailsForAvailableAreaService.getRoomDetailsByUnitIdAndAvailableAreaId(reservationsToSave.getUnit().getId(), reservationsToSave.getAvailableArea().getId());
                if (roomDetailsForAvailableArea.getId() != null) {
                    // Update the price based on available area details
                    System.out.println("roomDetailsForAvailableArea.getNewPrice(): "+roomDetailsForAvailableArea.getNewPrice());
                    System.out.println("roomDetailsForAvailableArea.getOldPrice(): "+ roomDetailsForAvailableArea.getOldPrice());
                    reservationsToSave.setPrice(roomDetailsForAvailableArea.getNewPrice());
                }
            }

            else if (reservationRequestDto.getAvailableAreaId() == null && reservationRequestDto.getRoomAvailableId() == null) {

                Unit unit = unitService.getUnitById(reservationRequestDto.getUnitId());

                reservationsToSave.setPrice(unit.getPrice());

            }

            System.out.println("Reservation Price: " + reservationsToSave.getPrice());

            Long userId = reservationRequestDto.getUserId();

            // Save the unit in the database
            reservationService.saveReservation(userId,reservationsToSave);

            // Check if the reservation has room available or available area

//            PushNotificationRequest notificationRequest = new PushNotificationRequest("رسالة من النظام","تم ارسال طلب حجز الوحدة: " + reservationsToSave.getUnit().getNameUnit(),userId);
//            PushNotificationRequest notificationRequest = new PushNotificationRequest("رسالة من النظام",LocaleContextHolder.getLocale()),messageSource.getMessage("notification_body_units.message", null, LocaleContextHolder.getLocale()) + " " + savedUnit.getNameUnit()"تم قبول طلب اضافة وحدة ",userId);
            PushNotificationRequest notificationRequest = new PushNotificationRequest(messageSource.getMessage("notification_title.message", null, LocaleContextHolder.getLocale()),messageSource.getMessage("notification_body_reservation.message", null, LocaleContextHolder.getLocale()) + " " + reservationsToSave.getUnit().getNameUnit(),userId);
            notificationService.processNotificationForGuest(notificationRequest);

            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(200, messageSource.getMessage("Successful_Reservation.message", null, LocaleContextHolder.getLocale())) );
        } catch (Exception e) {
//            // Log the exception
            logger.error("Error occurred while processing create-reservation request", e);

            System.out.println("Error Message: " + e);
            // Return user-friendly error response
            ApiResponse response = new ApiResponse(400, messageSource.getMessage("Failed_Reservation.message", null, LocaleContextHolder.getLocale()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseGetId> getReservationById(@PathVariable Long id) {


        Reservations reservation = reservationService.getReservationById(id);
        if (reservation == null) {
            return ResponseEntity.notFound().build();
        }
        ReservationResponseGetId response = reservationGetByIdMapper.toReservationDto(reservation);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/Status-Reservation")
    public ResponseEntity<?> getReservationsForUserAndStatus(
            @RequestParam(name = "USER_ID") Long userId,
            @RequestParam(name = "statusUnitId") Long statusUnitId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
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

        Page<Reservations> reservations = reservationService.getReservationForUserAndStatus(userId, statusUnitId, pageable);

        if (!reservations.isEmpty()) {
            List<ReservationStatus> reservationRequestDtoList = reservationStatusMapper.toReservationStatusDtoList(reservations.getContent());
            return new ResponseEntity<>(reservationRequestDtoList, HttpStatus.OK);
        } else {
            ApiResponse response = new ApiResponse(200, messageSource.getMessage("no_content.message", null, LocaleContextHolder.getLocale()));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping("/Insert-Evaluation-Reservation")
    public ResponseEntity<?> getReservationForEvaluation(
            @RequestParam(name = "USER_ID") Long userId,
            @RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader
    ) {

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

        LocalDate currentDate = LocalDate.now();

        List<Reservations> reservations = reservationService.findReservationsByDepartureDateBeforeAndUserIdAndNotEvaluating(currentDate, userId);

        if (!reservations.isEmpty()) {
            List<ReservationStatus> reservationRequestDtoList = reservationStatusMapper.toReservationStatusDtoList(reservations);
            return new ResponseEntity<>(reservationRequestDtoList, HttpStatus.OK);
        } else {
            ApiResponse response = new ApiResponse(204, messageSource.getMessage("no_content.message", null, LocaleContextHolder.getLocale()));
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }
    }

    @PatchMapping("/{reservationId}/set-evaluation")
    public ResponseEntity<?> setEvaluation(@PathVariable Long reservationId,
                                           @RequestParam Long evaluationId,
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

    Reservations existingReservation = reservationService.getReservationById(reservationId);
    Unit unit = reservationService.findUnitByReservationId(reservationId);

    Long userId = existingReservation.getUser().getId();

    if (existingReservation == null) {
        return ResponseEntity.notFound().build();
    }

    Evaluation evaluation = evaluationRepository.findById(evaluationId).orElse(null);
    if (evaluation == null) {
        return ResponseEntity.badRequest().body(messageSource.getMessage("No_Evaluation_Founded.message", null, LocaleContextHolder.getLocale()));
    }

    // Set the Evaluation for the Reservation
    existingReservation.setEvaluation(evaluation);
    existingReservation.setIsEvaluating(true);

    unitService.updateEvaluationsForUnits(unit.getId());

    try {
        // Save the updated Reservation
        reservationService.saveReservation(userId, existingReservation);
        return ResponseEntity.ok().body(messageSource.getMessage("Evaluation_Set_Successfully.message", null, LocaleContextHolder.getLocale()));
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageSource.getMessage("Evaluation_Set_Failed.message", null, LocaleContextHolder.getLocale()));
    }

}

    @DeleteMapping("Delete/Reservation/{id}")
    public ResponseEntity<?> deleteUnit(@PathVariable Long id,
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

        Reservations reservations = reservationRepository.findById(id).orElse(null);

        StatusUnit statusUnit = statusRepository.findById(4L).orElse(null);

        reservations.setStatusUnit(statusUnit);

        reservationRepository.save(reservations);

        ApiResponse response = new ApiResponse(200, messageSource.getMessage("Reservation_deleted.message", null, LocaleContextHolder.getLocale()));
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

//    @DeleteMapping("Delete/Reservation/{id}")
//    public ResponseEntity<?> deleteReservation(@PathVariable Long id) {
//
//        try {
//            reservationService.updateStatusForReservation(id, 4L);
//            reservationService.deleteUnit(id);
//            ApiResponse response = new ApiResponse(200, "Reservation_Deleted_Successfully.message!");
//            return ResponseEntity.status(HttpStatus.OK).body(response);
//        } catch (Exception e) {
//            ApiResponse response = new ApiResponse(404, "Not_found.message");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//        }
//    }

}
