package com.AlTaraf.Booking.rest.controller;

import com.AlTaraf.Booking.database.entity.*;
import com.AlTaraf.Booking.database.repository.EvaluationRepository;
import com.AlTaraf.Booking.database.repository.ReservationRepository;
import com.AlTaraf.Booking.database.repository.StatusRepository;
import com.AlTaraf.Booking.database.repository.UserRepository;
import com.AlTaraf.Booking.rest.dto.*;
import com.AlTaraf.Booking.rest.mapper.CommentMapper;
import com.AlTaraf.Booking.rest.mapper.ReservationGetByIdMapper;
import com.AlTaraf.Booking.rest.mapper.ReservationRequestMapper;
import com.AlTaraf.Booking.rest.mapper.ReservationStatusMapper;
import com.AlTaraf.Booking.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    RoomDetailsService roomDetailsService;

    @Autowired
    RoomDetailsForAvailableAreaService roomDetailsForAvailableAreaService;

    @Autowired
    EvaluationRepository evaluationRepository;

    @Autowired
    ReservationStatusMapper reservationStatusMapper;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    NotificationService notificationService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    CommentService commentService;


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

            Long userId = reservationRequestDto.getUserId();

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

            // Convert UnitRequestDto to Unit
            Reservations reservationsToSave = reservationRequestMapper.toReservation(reservationRequestDto);


            if (reservationRequestDto.getRoomAvailableId() != null) {
                RoomDetails roomDetails = roomDetailsService.getRoomDetailsByUnitIdAndRoomAvailableId(reservationRequestDto.getUnitId(), reservationRequestDto.getRoomAvailableId());
                if (roomDetails != null) {
                    // Update the price based on room details
                    reservationsToSave.setPrice(roomDetails.getNewPrice());
                }
            }


            else if (reservationRequestDto.getAvailableAreaId() != null) {
                RoomDetailsForAvailableArea roomDetailsForAvailableArea = roomDetailsForAvailableAreaService.getRoomDetailsByUnitIdAndAvailableAreaId(reservationsToSave.getUnit().getId(), reservationsToSave.getAvailableArea().getId());
                if (roomDetailsForAvailableArea.getId() != null) {
                    // Update the price based on available area details
                    reservationsToSave.setPrice(roomDetailsForAvailableArea.getNewPrice());
                }
            }

            else {
                Unit unit = unitService.getUnitById(reservationRequestDto.getUnitId());
                reservationsToSave.setPrice(unit.getPrice());
            }

            Unit unit = unitService.getUnitById(reservationRequestDto.getUnitId());

            Long userIdLessor = unit.getUser().getId();

            if (user.getWallet() < reservationsToSave.getCommision()) {
                ApiResponse response = new ApiResponse(400, messageSource.getMessage("Failed_Add_Reservation.message", null, LocaleContextHolder.getLocale()));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

//            reservationsToSave.setCreatedDate(DateUtils.getCurrentDate());

            // Save the reservation in the database
            Reservations reservation = reservationService.saveReservation(reservationsToSave);

            PushNotificationRequest notificationRequest = new PushNotificationRequest(messageSource.getMessage("notification_title.message", null, LocaleContextHolder.getLocale()),
                    messageSource.getMessage("notification_body_reservation.message", null,
                            LocaleContextHolder.getLocale()) + " " + unit.getNameUnit() ,userId, null, reservation.getId(), null);

            notificationService.processNotificationForGuest(notificationRequest);

            PushNotificationRequest notificationRequest2 = new PushNotificationRequest(messageSource.getMessage("notification_title.message", null, LocaleContextHolder.getLocale()),
                    messageSource.getMessage("notification_body_reservation_request.message", null,
                            LocaleContextHolder.getLocale()) + " " + unit.getNameUnit() ,userIdLessor,
                    null, reservation.getId(), null);
            notificationService.processNotification(notificationRequest2);



            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(200, messageSource.getMessage("Successful_Reservation.message", null, LocaleContextHolder.getLocale())) );
        } catch (Exception e) {
//            // Log the exception
            logger.error("Error occurred while processing create-reservation request", e);

            System.out.println("Catch Error Message: " + e);
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

    @GetMapping("/status-reservation-for-dashboard")
    public ResponseEntity<?> reservationStatusForDashboard(
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

        Page<Reservations> reservations = reservationService.getReservationForStatus(statusUnitId, pageable);

        if (!reservations.isEmpty()) {
            List<ReservationDashboard> reservationRequestDtoList = reservationStatusMapper.toReservationDashboardDto(reservations.getContent());
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
                                           @RequestBody String content,
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

    User user = userRepository.findById(userId).orElse(null);

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

    CommentRequest commentRequest = new CommentRequest();
    commentRequest.setUnitId(unit.getId());
        assert user != null;
        commentRequest.setUserName(user.getUsername());
        if (user.getFileForProfile() != null) {
            commentRequest.setFileDownloadUri(user.getFileForProfile().getFileDownloadUri());
        } else {
            commentRequest.setFileDownloadUri(null); // or some default value, if needed
        }
        commentRequest.setPhoneNumber(user.getPhone());
    commentRequest.setContent(content);

    Comment commentToSave = commentMapper.toComment(commentRequest);

    commentService.saveComment(commentToSave);

    try {
        // Save the updated Reservation
        reservationService.saveReservation(existingReservation);
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


    @GetMapping("/get-current-reservations")
    public ResponseEntity<?> getUserReservationsLesserArrivalDate( @RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "2") int size,
                                                                    @RequestParam(name = "userId", required = false) Long userId) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        Page<Reservations> reservations = reservationService.getUserReservationsLesserArrivalDate(userId, pageable);

        List<ReservationStatus> reservationRequestDtoList = reservationStatusMapper.toReservationStatusDtoList(reservations.getContent());

        return new ResponseEntity<>(reservationRequestDtoList, HttpStatus.OK);
    }

    @GetMapping("/get-previously-reservations")
    public ResponseEntity<?> getUserReservationsGreaterArrivalDate(@RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "2") int size,
                                                                    @RequestParam(name = "userId", required = false) Long userId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        Page<Reservations> reservations = reservationService.getUserReservationsGreaterArrivalDate(userId, pageable);

        List<ReservationStatus> reservationRequestDtoList = reservationStatusMapper.toReservationStatusDtoList(reservations.getContent());

        return new ResponseEntity<>(reservationRequestDtoList, HttpStatus.OK);

    }

}
