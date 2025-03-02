package com.AlTaraf.Booking.rest.controller;


import com.AlTaraf.Booking.database.entity.*;
import com.AlTaraf.Booking.database.repository.*;
import com.AlTaraf.Booking.rest.dto.*;
import com.AlTaraf.Booking.rest.mapper.ReserveDateHallsMapper;
import com.AlTaraf.Booking.rest.mapper.ReserveDateMapper;
import com.AlTaraf.Booking.rest.mapper.ReserveDateRoomDetailsMapper;
import com.AlTaraf.Booking.rest.mapper.ReserveDateUnitMapper;
import com.AlTaraf.Booking.service.ReserveDateHallsService;
import com.AlTaraf.Booking.service.ReserveDateUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/calender")
public class CalenderController {

    @Autowired
    ReserveDateRepository reserveDateRepository;

    @Autowired
    ReserveDateRoomDetailsRepository reserveDateRoomDetailsRepository;

    @Autowired
    ReserveDateHallsService reserveDateHallsService;

    @Autowired
    ReserveDateMapper reserveDateMapper;

    @Autowired
    ReserveDateHallsMapper reserveDateHallsMapper;

    @Autowired
    RoomDetailsForAvailableAreaRepository roomDetailsForAvailableAreaRepository;

    @Autowired
    RoomDetailsRepository roomDetailsRepository;

    @Autowired
    UnitRepository unitRepository;

    @Autowired
    MessageSource messageSource;

    @Autowired
    ReserveDateUnitMapper reserveDateUnitMapper;

    @Autowired
    ReserveDateUnitService reserveDateUnitService;

    @Autowired
    ReserveDateRoomDetailsMapper reserveDateRoomDetailsMapper;

    @PostMapping("/reserve-date-halls-for-add-unit")
    public ResponseEntity<?> createReserveDateForHalls(@RequestBody ReserveDateHallsRequest reserveDateHallsDto,
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

            ReserveDateHalls reserveDateHalls = ReserveDateHallsMapper.INSTANCE.toEntity(reserveDateHallsDto);
            reserveDateHallsService.save(reserveDateHalls);
            return ResponseEntity.ok(new ApiResponse(201, messageSource.getMessage("reserve_date_success.message", null, LocaleContextHolder.getLocale())));
        } catch (Exception e) {
            System.out.println("Failed Reserve Date Halls: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, messageSource.getMessage("Reserve_Date_Fail.message", null, LocaleContextHolder.getLocale())) );
        }
    }

    @PostMapping("/edit-reserve-date-halls")
    public ResponseEntity<?> editReserveDateForHalls(@RequestBody ReserveDateHallsRequest reserveDateHallsDto,
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

            List<ReserveDateHalls> reserveDateHallsList = reserveDateHallsService.getByUnitId(reserveDateHallsDto.getUnitId());
            for (ReserveDateHalls reserveDateHalls: reserveDateHallsList) {
                reserveDateHallsService.deleteDateInfoHallsByReserveDateHallsId(reserveDateHalls.getId());
            }

            reserveDateHallsService.deleteByUnitId(reserveDateHallsDto.getUnitId());

            ReserveDateHalls reserveDateHalls = ReserveDateHallsMapper.INSTANCE.toEntity(reserveDateHallsDto);
            reserveDateHallsService.save(reserveDateHalls);
            return ResponseEntity.ok(new ApiResponse(201, messageSource.getMessage("reserve_date_success.message", null, LocaleContextHolder.getLocale())));
        } catch (Exception e) {
            System.out.println("Failed Reserve Date Halls: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, messageSource.getMessage("Reserve_Date_Fail.message", null, LocaleContextHolder.getLocale())) );
        }
    }


    @DeleteMapping("/delete-reserve-date-halls")
    public ResponseEntity<?> deleteReserveDateForHalls(@RequestBody ReserveDateHallsDeleteDto reserveDateHallsDeleteDto,
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

            List<ReserveDateHalls> reserveDateHallsList = reserveDateHallsService.getByUnitId(reserveDateHallsDeleteDto.getUnitId());
            for (ReserveDateHalls reserveDateHalls: reserveDateHallsList) {
                reserveDateHallsService.deleteDateInfoHallsByReserveDateHallsId(reserveDateHalls.getId());
            }

            reserveDateHallsService.deleteByUnitId(reserveDateHallsDeleteDto.getUnitId());

            return ResponseEntity.ok(new ApiResponse(201, messageSource.getMessage("reserve_date_deleted_success.message", null, LocaleContextHolder.getLocale())));
        } catch (Exception e) {
            System.out.println("Failed Reserve Date Halls: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, messageSource.getMessage("Reserve_Date_Fail.message", null, LocaleContextHolder.getLocale())) );
        }
    }
    @PostMapping
    public ResponseEntity<?> createReserveDate(@RequestBody ReserveDateRequest reserveDateRequest,
                                               @RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader) {
        try {
            Locale locale = LocaleContextHolder.getLocale();

            if (acceptLanguageHeader != null && !acceptLanguageHeader.isEmpty()) {
                try {
                    List<Locale.LanguageRange> languageRanges = Locale.LanguageRange.parse(acceptLanguageHeader);
                    if (!languageRanges.isEmpty()) {
                        locale = Locale.forLanguageTag(languageRanges.get(0).getRange());
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("IllegalArgumentException: " + e);
                }
            }

            if (reserveDateRequest.getRoomDetailsForAvailableAreaId() != null) {
                RoomDetailsForAvailableArea roomDetailsForAvailableArea = null;

                RoomDetails roomDetails = null;
                System.out.println("getRoomDetailsForAvailableAreaId");

                Optional<RoomDetailsForAvailableArea> roomDetailsForAvailableAreaOptional = roomDetailsForAvailableAreaRepository.findById(reserveDateRequest.getRoomDetailsForAvailableAreaId());
                roomDetailsForAvailableArea = roomDetailsForAvailableAreaOptional.orElse(null);

                Optional<Unit> unitOptional = unitRepository.findById(reserveDateRequest.getUnitId());
                Unit unit = unitOptional.orElseThrow(() -> new RuntimeException("Unit not found"));

                if (roomDetailsForAvailableArea != null) {
                    roomDetailsForAvailableArea.setUnit(unit);
                }

                if (roomDetails != null) {
                    roomDetails.setUnit(unit);
                }

                ReserveDate reserveDate = reserveDateMapper.INSTANCE.reserveDateRequestToReserveDate(reserveDateRequest);

                reserveDate.setRoomDetailsForAvailableArea(roomDetailsForAvailableArea);

                reserveDateRepository.save(reserveDate);
            }

            else if (reserveDateRequest.getRoomDetailsId() != null) {

                RoomDetails roomDetails = null;

                Optional<RoomDetails> roomDetailsOptional = roomDetailsRepository.findById(reserveDateRequest.getRoomDetailsId());
                roomDetails = roomDetailsOptional.orElse(null);

                assert roomDetails != null;
                System.out.println("getRoomDetailsId: " + roomDetails.getId());

                Optional<Unit> unitOptional = unitRepository.findById(reserveDateRequest.getUnitId());
                Unit unit = unitOptional.orElseThrow(() -> new RuntimeException("Unit not found"));

                System.out.println("unit : " + unit.getId());
                if (roomDetails != null) {
                    roomDetails.setUnit(unit);
                }

                ReserveDateRoomDetails reserveDateRoomDetails = reserveDateRoomDetailsMapper.INSTANCE.reserveDateRequestToReserveDateRoomDetails(reserveDateRequest);

                System.out.println("roomDetails Id : " + roomDetails.getId());
                System.out.println("roomDetails Unit Id : " + roomDetails.getUnit().getId());
                reserveDateRoomDetails.setRoomDetails(roomDetails);
                reserveDateRoomDetailsRepository.save(reserveDateRoomDetails);
            }

            else {
                ReserveDateUnit reserveDateUnit = reserveDateUnitMapper.INSTANCE.reserveDateRequestToReserveDate(reserveDateRequest);
                reserveDateUnitService.save(reserveDateUnit);
            }
            return ResponseEntity.ok(new ApiResponse(201, messageSource.getMessage("reserve_date_success.message", null, LocaleContextHolder.getLocale())));
        } catch (Exception e) {
            System.out.println("Failed Reserve Date: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, messageSource.getMessage("Reserve_Date_Fail.message", null, LocaleContextHolder.getLocale())));
        }
    }

    @PostMapping("edit")
    public ResponseEntity<?> editReserveDate(@RequestBody ReserveDateRequest reserveDateRequest,
                                               @RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader) {
        try {
            Locale locale = LocaleContextHolder.getLocale();

            if (acceptLanguageHeader != null && !acceptLanguageHeader.isEmpty()) {
                try {
                    List<Locale.LanguageRange> languageRanges = Locale.LanguageRange.parse(acceptLanguageHeader);
                    if (!languageRanges.isEmpty()) {
                        locale = Locale.forLanguageTag(languageRanges.get(0).getRange());
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("IllegalArgumentException: " + e);
                }
            }

            if (reserveDateRequest.getRoomDetailsForAvailableAreaId() != null) {

                List<ReserveDate> reserveDateList = reserveDateRepository.findListByUnitId(reserveDateRequest.getUnitId());
                for (ReserveDate reserveDate : reserveDateList) {
                    reserveDateRepository.deleteDateInfoByReserveDateId(reserveDate.getId());
                }
                reserveDateRepository.deleteByUnitId(reserveDateRequest.getUnitId());

                RoomDetailsForAvailableArea roomDetailsForAvailableArea = null;

                RoomDetails roomDetails = null;
                System.out.println("getRoomDetailsForAvailableAreaId");

                Optional<RoomDetailsForAvailableArea> roomDetailsForAvailableAreaOptional = roomDetailsForAvailableAreaRepository.findById(reserveDateRequest.getRoomDetailsForAvailableAreaId());
                roomDetailsForAvailableArea = roomDetailsForAvailableAreaOptional.orElse(null);


                Optional<Unit> unitOptional = unitRepository.findById(reserveDateRequest.getUnitId());
                Unit unit = unitOptional.orElseThrow(() -> new RuntimeException("Unit not found"));

                if (roomDetailsForAvailableArea != null) {
                    roomDetailsForAvailableArea.setUnit(unit);
                }

                if (roomDetails != null) {
                    roomDetails.setUnit(unit);
                }
                ReserveDate reserveDate = ReserveDateMapper.INSTANCE.reserveDateRequestToReserveDate(reserveDateRequest);

                reserveDate.setRoomDetailsForAvailableArea(roomDetailsForAvailableArea);

                reserveDateRepository.save(reserveDate);
            }

            else if (reserveDateRequest.getRoomDetailsId() != null) {

                List<ReserveDateRoomDetails> reserveDateRoomDetails = reserveDateRoomDetailsRepository.findListByUnitId(reserveDateRequest.getUnitId());
                for (ReserveDateRoomDetails reserveDateRoomDetails1 : reserveDateRoomDetails) {
                    reserveDateRoomDetailsRepository.deleteDateInfoByReserveDateId(reserveDateRoomDetails1.getId());
                }
                reserveDateRoomDetailsRepository.deleteByUnitId(reserveDateRequest.getUnitId());


                RoomDetails roomDetails = null;

                Optional<RoomDetails> roomDetailsOptional = roomDetailsRepository.findById(reserveDateRequest.getRoomDetailsId());
                roomDetails = roomDetailsOptional.orElse(null);
                assert roomDetails != null;
                System.out.println("getRoomDetailsId: " + roomDetails.getId());

                Optional<Unit> unitOptional = unitRepository.findById(reserveDateRequest.getUnitId());
                Unit unit = unitOptional.orElseThrow(() -> new RuntimeException("Unit not found"));

                System.out.println("unit : " + unit.getId());
                if (roomDetails != null) {
                    roomDetails.setUnit(unit);
                }

                ReserveDateRoomDetails reserveDateRoomDetails2 = reserveDateRoomDetailsMapper.INSTANCE.reserveDateRequestToReserveDateRoomDetails(reserveDateRequest);

                System.out.println("roomDetails Id : " + roomDetails.getId());
                System.out.println("roomDetails Unit Id : " + roomDetails.getUnit().getId());
                reserveDateRoomDetails2.setRoomDetails(roomDetails);

                reserveDateRoomDetailsRepository.save(reserveDateRoomDetails2);

            }

            else {

                List<ReserveDateUnit> reserveDateUnits = reserveDateUnitService.getListByUnitId(reserveDateRequest.getUnitId());
                for (ReserveDateUnit reserveDateRoomDetails1 : reserveDateUnits) {
                    reserveDateUnitService.deleteDateInfoByReserveDateId(reserveDateRoomDetails1.getId());
                }
                reserveDateUnitService.deleteByUnitId(reserveDateRequest.getUnitId());

                ReserveDateUnit reserveDateUnit = reserveDateUnitMapper.INSTANCE.reserveDateRequestToReserveDate(reserveDateRequest);
                reserveDateUnitService.save(reserveDateUnit);
            }

            return ResponseEntity.ok(new ApiResponse(201, messageSource.getMessage("reserve_date_success.message", null, LocaleContextHolder.getLocale())));
            } catch(Exception e){
            System.out.println("Failed Reserve Date: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, messageSource.getMessage("Reserve_Date_Fail.message", null, LocaleContextHolder.getLocale())));
        }
    }

    @DeleteMapping("delete-reserve-date")
    public ResponseEntity<?> deleteReserveDate(@RequestBody ReserveDateDeleteDto reserveDateDeleteDto,
                                             @RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader) {
        try {
            Locale locale = LocaleContextHolder.getLocale();

            if (acceptLanguageHeader != null && !acceptLanguageHeader.isEmpty()) {
                try {
                    List<Locale.LanguageRange> languageRanges = Locale.LanguageRange.parse(acceptLanguageHeader);
                    if (!languageRanges.isEmpty()) {
                        locale = Locale.forLanguageTag(languageRanges.get(0).getRange());
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("IllegalArgumentException: " + e);
                }
            }

            if (reserveDateDeleteDto.getRoomDetailsForAvailableAreaId() != null) {

                List<ReserveDate> reserveDateList = reserveDateRepository.findListByUnitId(reserveDateDeleteDto.getUnitId());
                for (ReserveDate reserveDate : reserveDateList) {
                    reserveDateRepository.deleteDateInfoByReserveDateId(reserveDate.getId());
                }
                reserveDateRepository.deleteByUnitId(reserveDateDeleteDto.getUnitId());
            }

            else if (reserveDateDeleteDto.getRoomDetailsId() != null) {

                List<ReserveDateRoomDetails> reserveDateRoomDetails = reserveDateRoomDetailsRepository.findListByUnitId(reserveDateDeleteDto.getUnitId());
                for (ReserveDateRoomDetails reserveDateRoomDetails1 : reserveDateRoomDetails) {
                    reserveDateRoomDetailsRepository.deleteDateInfoByReserveDateId(reserveDateRoomDetails1.getId());
                }
                reserveDateRoomDetailsRepository.deleteByUnitId(reserveDateDeleteDto.getUnitId());
            }

            else {
                List<ReserveDateUnit> reserveDateUnits = reserveDateUnitService.getListByUnitId(reserveDateDeleteDto.getUnitId());
                for (ReserveDateUnit reserveDateRoomDetails1 : reserveDateUnits) {
                    reserveDateUnitService.deleteDateInfoByReserveDateId(reserveDateRoomDetails1.getId());
                }
                reserveDateUnitService.deleteByUnitId(reserveDateDeleteDto.getUnitId());
            }

            return ResponseEntity.ok(new ApiResponse(201, messageSource.getMessage("reserve_date_deleted_success.message", null, LocaleContextHolder.getLocale())));
        } catch(Exception e){
            System.out.println("Failed Reserve Date: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, messageSource.getMessage("Reserve_Date_Fail.message", null, LocaleContextHolder.getLocale())));
        }
    }

    @GetMapping("/{unitId}")
    public ResponseEntity<?> getReserveDatesByRoomDetailsForAvailableAreaIdAndUnitId(@RequestParam(name = "roomDetailsForAvailableAreaId", required = false) Long roomDetailsForAvailableAreaId,
                                                                                     @RequestParam(name = "roomDetailsId", required = false) Long roomDetailsId,
                                                                                     @PathVariable Long unitId,
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

            List<ReserveDateHalls> reserveDateHallsList = reserveDateHallsService.getByUnitIdAndReserveIsTrue(unitId);

            List<ReserveDateHalls> reserveDateHallsListForTrader = reserveDateHallsService.getByUnitId(unitId);

            if (roomDetailsForAvailableAreaId != null) {
                boolean roomNumberZeroExists = roomDetailsForAvailableAreaRepository.existsByRoomNumberZero();

                System.out.println("roomNumberZeroExists: " + roomNumberZeroExists);

                if (roomNumberZeroExists) {
                    System.out.println("roomNumberZeroExists Is True");
                    List<ReserveDate> reserveDates = reserveDateRepository.findByRoomDetailsForAvailableAreaIdAndUnitId(roomDetailsForAvailableAreaId, unitId);
                    List<ReserveDateDto> reserveDateRequests = reserveDates.stream()
                            .map(reserveDate -> {
                                // Convert entity to DTO
                                ReserveDateDto dto = ReserveDateMapper.INSTANCE.reserveDateToReserveDateRequest(reserveDate);

                                // Filter dateInfoList: keep only today or future dates
                                List<DateInfoDto> filteredDates = dto.getDateInfoList().stream()
                                        .filter(dateInfo -> {
                                            LocalDate date = LocalDate.parse(dateInfo.getDate(), DateTimeFormatter.ISO_LOCAL_DATE);
                                            return !date.isBefore(LocalDate.now()); // Keep today or future dates
                                        })
                                        .collect(Collectors.toList());

                                // Return a new DTO with the filtered list
                                return new ReserveDateDto(filteredDates, dto.getRoomDetailsForAvailableAreaId(), dto.getUnitId());
                            })
                            .filter(dto -> !dto.getDateInfoList().isEmpty()) // Remove DTOs where dateInfoList is empty after filtering
                            .collect(Collectors.toList());
                    return ResponseEntity.ok(reserveDateRequests);
                } else  {
                    List<ReserveDate> reserveDateList = reserveDateRepository.findByRoomDetailsForAvailableAreaIdAndUnitId(roomDetailsForAvailableAreaId, unitId);
                    List<ReserveDateDto> reserveDateRequests = reserveDateList.stream()
                            .map(reserveDate -> {
                                // Convert entity to DTO
                                ReserveDateDto dto = ReserveDateMapper.INSTANCE.reserveDateToReserveDateRequest(reserveDate);

                                // Filter dateInfoList: keep only today or future dates
                                List<DateInfoDto> filteredDates = dto.getDateInfoList().stream()
                                        .filter(dateInfo -> {
                                            LocalDate date = LocalDate.parse(dateInfo.getDate(), DateTimeFormatter.ISO_LOCAL_DATE);
                                            return !date.isBefore(LocalDate.now()); // Keep today or future dates
                                        })
                                        .collect(Collectors.toList());

                                // Return a new DTO with the filtered list
                                return new ReserveDateDto(filteredDates, dto.getRoomDetailsForAvailableAreaId(), dto.getUnitId());
                            })
                            .filter(dto -> !dto.getDateInfoList().isEmpty()) // Remove DTOs where dateInfoList is empty after filtering
                            .collect(Collectors.toList());
                    return ResponseEntity.ok(reserveDateRequests);
                }

            }

            else if (roomDetailsId != null) {
                boolean roomNumberZeroExists = roomDetailsRepository.existsByRoomNumberZero();
                System.out.println("roomNumberZeroExists: " + roomNumberZeroExists);

                if (roomNumberZeroExists) {
                    System.out.println("roomNumberZeroExists Is True");
                    List<ReserveDateRoomDetails> reserveDateRoomDetails = reserveDateRoomDetailsRepository.findByRoomDetailsIdAndUnitId(roomDetailsId, unitId);
                    List<ReserveDateRoomDetailsDto> reserveDateRoomDetailsDtoList = reserveDateRoomDetails.stream()
                            .map(reserveDateRoomDetails2 -> {
                                // Convert entity to DTO
                                ReserveDateRoomDetailsDto dto = ReserveDateRoomDetailsMapper.INSTANCE.reserveDateRoomDetailsToReserveDateRequest(reserveDateRoomDetails2);

                                // Filter dateInfoList where date >= today
                                List<DateInfoDto> filteredDates = dto.getDateInfoList().stream()
                                        .filter(dateInfo -> {
                                            LocalDate date = LocalDate.parse(dateInfo.getDate(), DateTimeFormatter.ISO_LOCAL_DATE);
                                            return !date.isBefore(LocalDate.now()); // Keep only today or future dates
                                        })
                                        .collect(Collectors.toList());

                                // Return a new DTO with filtered dates
                                return new ReserveDateRoomDetailsDto(filteredDates, dto.getRoomDetailsId(), dto.getUnitId());
                            })
                            .filter(dto -> !dto.getDateInfoList().isEmpty()) // Remove DTOs with empty date lists
                            .collect(Collectors.toList());
                    return ResponseEntity.ok(reserveDateRoomDetailsDtoList);
                }

                else {
                    List<ReserveDateRoomDetails> reserveDateRoomDetailsList = reserveDateRoomDetailsRepository.findByRoomDetailsIdAndUnitId(roomDetailsId, unitId);

                    List<ReserveDateRoomDetailsDto> reserveDateRoomDetailsDtoList = reserveDateRoomDetailsList.stream()
                            .map(reserveDateRoomDetails2 -> {
                                // Convert entity to DTO
                                ReserveDateRoomDetailsDto dto = ReserveDateRoomDetailsMapper.INSTANCE.reserveDateRoomDetailsToReserveDateRequest(reserveDateRoomDetails2);

                                // Filter dateInfoList where date >= today
                                List<DateInfoDto> filteredDates = dto.getDateInfoList().stream()
                                        .filter(dateInfo -> {
                                            LocalDate date = LocalDate.parse(dateInfo.getDate(), DateTimeFormatter.ISO_LOCAL_DATE);
                                            return !date.isBefore(LocalDate.now()); // Keep only today or future dates
                                        })
                                        .collect(Collectors.toList());

                                // Return a new DTO with filtered dates
                                return new ReserveDateRoomDetailsDto(filteredDates, dto.getRoomDetailsId(), dto.getUnitId());
                            })
                            .filter(dto -> !dto.getDateInfoList().isEmpty()) // Remove DTOs with empty date lists
                            .collect(Collectors.toList());
                    return ResponseEntity.ok(reserveDateRoomDetailsDtoList);
                }

            }

            else if (!reserveDateHallsList.isEmpty()) {

                List<ReserveDateHallsRequest> reserveDateHallsDtoList = reserveDateHallsList.stream()
                        .map(ReserveDateHallsMapper.INSTANCE::toDto) // Convert to DTO first
                        .map(dto -> {
                            // Filter dateInfoList where date >= today
                            List<DateInfoHallsRequest> filteredDates = dto.getDateInfoList().stream()
                                    .filter(dateInfo -> !dateInfo.getDate().isBefore(LocalDate.now()))
                                    .collect(Collectors.toList());

                            // Return a new DTO with filtered dates (without modifying the original object)
                            return new ReserveDateHallsRequest(filteredDates, dto.getUnitId());
                        })
                        .filter(dto -> !dto.getDateInfoList().isEmpty()) // Remove objects with no valid dates
                        .collect(Collectors.toList());
                return ResponseEntity.ok(reserveDateHallsDtoList);
            }

            else if (!reserveDateHallsListForTrader.isEmpty()) {
                List<ReserveDateHallsRequest> reserveDateHallsDtoList = reserveDateHallsListForTrader.stream()
                        .map(ReserveDateHallsMapper.INSTANCE::toDto) // Convert to DTO first
                        .map(dto -> {
                            // Filter dateInfoList where date >= today
                            List<DateInfoHallsRequest> filteredDates = dto.getDateInfoList().stream()
                                    .filter(dateInfo -> !dateInfo.getDate().isBefore(LocalDate.now()))
                                    .collect(Collectors.toList());

                            // Return a new DTO with filtered dates (without modifying the original object)
                            return new ReserveDateHallsRequest(filteredDates, dto.getUnitId());
                        })
                        .filter(dto -> !dto.getDateInfoList().isEmpty()) // Remove objects with no valid dates
                        .collect(Collectors.toList());
                return ResponseEntity.ok(reserveDateHallsDtoList);
            }

            else if (unitId != null && reserveDateHallsList.isEmpty() && roomDetailsForAvailableAreaId == null && roomDetailsId == null ) {

                System.out.println("unitId != null && reserveDateHallsList.isEmpty() && roomDetailsForAvailableAreaId == null && roomDetailsId == null");
                List<ReserveDateUnit> reserveDateUnits = reserveDateUnitService.getListByUnitId(unitId);
                List<ReserveDateUnitDto> reserveDateUnitDtoList = reserveDateUnits.stream()
                        .map(reserveDateUnit -> {
                            // Convert the entity to DTO
                            ReserveDateUnitDto dto = ReserveDateUnitMapper.INSTANCE.reserveDateUnitToReserveDateRequest(reserveDateUnit);

                            // Filter the dateInfoList in the DTO without modifying the original entity
                            List<DateInfoDto> filteredDates = dto.getDateInfoList().stream()
                                    .filter(dateInfo -> {
                                        LocalDate date = LocalDate.parse(dateInfo.getDate(), DateTimeFormatter.ISO_LOCAL_DATE);
                                        return !date.isBefore(LocalDate.now()); // Filter dates >= today
                                    })
                                    .collect(Collectors.toList());

                            // Create a new DTO with the filtered list (Immutable approach)
                            return new ReserveDateUnitDto(filteredDates, dto.getUnitId());
                        })
                        .filter(reserveDateUnitDto -> !reserveDateUnitDto.getDateInfoList().isEmpty()) // Remove units with no available dates
                        .collect(Collectors.toList());
                return ResponseEntity.ok(reserveDateUnitDtoList);

                // Return an empty list or appropriate response when no data is found
//                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, messageSource.getMessage("", null, LocaleContextHolder.getLocale())));
            }
            else {
                System.out.println("else");
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(204, messageSource.getMessage("No_Reserve_Dates_Found.message", null, LocaleContextHolder.getLocale())));
            }
        } catch (Exception e) {
            System.out.println("Failed to get Reserve Dates: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, messageSource.getMessage("Failed_Get_Reserve_Date.message", null, LocaleContextHolder.getLocale())));
        }
    }


    @GetMapping("/For-Add-Unit/{unitId}")
    public ResponseEntity<?> getReserveDatesByRoomDetailsForAvailableAreaIdAndUnitIdForUnit(@RequestParam(name = "roomDetailsForAvailableAreaId", required = false) Long roomDetailsForAvailableAreaId,
                                                                                            @RequestParam(name = "roomDetailsId", required = false) Long roomDetailsId,
                                                                                            @PathVariable Long unitId,
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

            List<ReserveDateHalls> reserveDateHallsList = reserveDateHallsService.getByUnitId(unitId);

            if (roomDetailsForAvailableAreaId != null) {
                List<ReserveDate> reserveDateList = reserveDateRepository.findByRoomDetailsForAvailableAreaIdAndUnitId(roomDetailsForAvailableAreaId, unitId);
                List<ReserveDateDto> reserveDateRequests = reserveDateList.stream()
                        .map(ReserveDateMapper.INSTANCE::reserveDateToReserveDateRequest)
                        .collect(Collectors.toList());
                return ResponseEntity.ok(reserveDateRequests);
            }

            else if (roomDetailsId != null) {
                List<ReserveDateRoomDetails> reserveDateRoomDetailsList = reserveDateRoomDetailsRepository.findByRoomDetailsIdAndUnitId(roomDetailsId, unitId);
                List<ReserveDateRoomDetailsDto> reserveDateRoomDetailsDtoList = reserveDateRoomDetailsList.stream()
                        .map(ReserveDateRoomDetailsMapper.INSTANCE::reserveDateRoomDetailsToReserveDateRequest)
                        .collect(Collectors.toList());
                return ResponseEntity.ok(reserveDateRoomDetailsDtoList);
            }

            else if (!reserveDateHallsList.isEmpty()) {
                List<ReserveDateHallsRequest> reserveDateHallsDtoList = reserveDateHallsList.stream()
                        .map(ReserveDateHallsMapper.INSTANCE::toDto)
                        .collect(Collectors.toList());
                return ResponseEntity.ok(reserveDateHallsDtoList);
            }

            else {
                List<ReserveDateUnit> reserveDateUnitList = reserveDateUnitService.getListByUnitId(unitId);

                List<ReserveDateUnitDto> reserveDateRequests = reserveDateUnitList.stream()
                        .map(ReserveDateUnitMapper.INSTANCE::reserveDateUnitToReserveDateRequest)
                        .collect(Collectors.toList());
                return ResponseEntity.ok(reserveDateRequests);
            }

        } catch (Exception e) {
            System.out.println("Failed to get Reserve Dates: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, messageSource.getMessage("Failed_Get_Reserve_Date.message", null, LocaleContextHolder.getLocale())));
        }
    }

}
