package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.database.entity.*;
import com.AlTaraf.Booking.database.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    RoomDetailsForAvailableAreaRepository roomDetailsForAvailableAreaRepository;

    @Autowired
    ReserveDateHallsRepository reserveDateHallsRepository;

    @Autowired
    RoomDetailsRepository roomDetailsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TotalTransactionsRepository totalTransactionsRepository;

    @Autowired
    TransactionsRepository transactionsRepository;

    @Autowired
    TransactionsDetailRepository transactionsDetailRepository;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    ReserveDateUnitRepository reserveDateUnitRepository;

    @Autowired
    DateInfoRepository dateInfoRepository;

    @Autowired
    DateInfoHallsRepository dateInfoHallsRepository;

    @Autowired
    ReservationPeriodUnitHallsRepository reservationPeriodUnitHallsRepository;

    @Autowired
    ReserveDateRoomDetailsRepository reserveDateRoomDetailsRepository;

    public Reservations saveReservation(Reservations reservations) {
        return reservationRepository.save(reservations);
    }

    public Reservations getReservationById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    public Unit findUnitByReservationId(Long reservationId){
        return reservationRepository.findUnitByReservationId(reservationId);
    }

    public void updateStatusForReservation(Long reservationId, Long statusUnitId) {

        Reservations reservations = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found with id: " + reservationId));

        LocalDate dateOfArrival = reservations.getDateOfArrival();
        LocalDate departureDate = reservations.getDepartureDate();

        Unit unit = findUnitByReservationId(reservationId);

        if ( unit.getUnitType().getId() == 2 && statusUnitId.equals(2L) ) {

            ReserveDateHalls reserveDateHall = reserveDateHallsRepository.findByUnitId(unit.getId());

            if (reserveDateHall != null) {

                DateInfoHalls dateInfoHalls = dateInfoHallsRepository.findByreserveDateHallsId(reserveDateHall.getId());

                if (unit.getPeriodCount() == 2) {

                    if (dateInfoHalls.isEvening()) {
                        dateInfoHalls.setMorning(true);
                    } else if (dateInfoHalls.isMorning()) {
                        dateInfoHalls.setEvening(true);
                    }
                    dateInfoHallsRepository.save(dateInfoHalls);
                }
            }

            if (unit.getPeriodCount() == 1) {

                ReserveDateHalls reserveDateHall2 = new ReserveDateHalls();
                reserveDateHall2.setUnit(unit);
                reserveDateHallsRepository.save(reserveDateHall2);

                DateInfoHalls dateInfoHalls1 = new DateInfoHalls();
                dateInfoHalls1.setReserveDateHalls(reserveDateHall2);
                dateInfoHalls1.setDate(dateOfArrival);

                ReservationPeriodUnitHalls reservationPeriodUnitHalls = reservationPeriodUnitHallsRepository.findByReservationId(reservationId);

                if (reservationPeriodUnitHalls.getAvailablePeriods().getId() == 1) {
                    dateInfoHalls1.setMorning(true);
                } else if (reservationPeriodUnitHalls.getAvailablePeriods().getId() == 2) {
                    dateInfoHalls1.setEvening(true);
                }
                dateInfoHallsRepository.save(dateInfoHalls1);
            }
        }


        if (unit.getAccommodationType() != null) {

            if (statusUnitId.equals(2L) && (unit.getAccommodationType().getId() == 4 || unit.getAccommodationType().getId() == 6 ||
                    unit.getAccommodationType().getId() == 3)) {
                ReserveDateUnit reserveDateUnit = new ReserveDateUnit();
                reserveDateUnit.setUnit(unit);
                reserveDateUnitRepository.save(reserveDateUnit);

                for (LocalDate date = dateOfArrival; date.isBefore(departureDate); date = date.plusDays(1)) {
                    System.out.println(date);
                    DateInfo dateInfo = new DateInfo();
                    dateInfo.setDate(date);
                    dateInfo.setReserveDateUnit(reserveDateUnit);
                    dateInfoRepository.save(dateInfo);
                }
            }
        }


        StatusUnit statusUnit = statusRepository.findById(statusUnitId)
                .orElseThrow(() -> new EntityNotFoundException("StatusUnit not found with id: " + statusUnitId));

        reservations.setStatusUnit(statusUnit);

        User user = reservations.getUser();
        System.out.println("user: " + user.getId());

        if (user.getWallet() > 0) {
            double currentWallentBalance = user.getWallet();
            currentWallentBalance -= reservations.getCommision();
            user.setWallet(currentWallentBalance);

            TotalTransactions totalTransactions = totalTransactionsRepository.findById(1L).orElse(null);

            Long totalReservationsTransactions = totalTransactions.getTotalReservationsTransactions();
            Long totalTransactionsNumber = totalTransactions.getTotalTransactions();
            totalReservationsTransactions++;
            totalTransactionsNumber++;

            totalTransactions.setTotalReservationsTransactions(totalReservationsTransactions);
            totalTransactions.setTotalTransactions(totalTransactionsNumber);

            totalTransactionsRepository.save(totalTransactions);

            Transactions transactions = transactionsRepository.findById(1L).orElse(null);

            TransactionsDetail transactionsDetail = new TransactionsDetail();
            transactionsDetail.setTransactions(transactions);
            transactionsDetail.setDate(new Date());
            transactionsDetail.setPhone(user.getPhone());
            transactionsDetail.setValue(reservations.getCommision());
            transactionsDetail.setUser(user);

            transactionsDetailRepository.save(transactionsDetail);

            Wallet wallet = new Wallet("تم حجز وحدة", "A unit has been reserved", reservations.getCommision() ,user, reservations.getUnit().getNameUnit(), "", "", false);
            walletRepository.save(wallet);
        }

        userRepository.save(user);

        reservationRepository.save(reservations);
    }

    public AvailableArea getAvailableAreaByReservations(Long reservationId) {
        return reservationRepository.findAvailableAreaIdByReservationId(reservationId);
    }

    public RoomAvailable getRoomAvailableByReservations(Long reservationId) {
        return reservationRepository.findRoomAvailableIdByReservationId(reservationId);
    }

    public Page<Reservations> getReservationForUserAndStatus(Long userId, Long statusUnitId , Pageable pageable) {
        return reservationRepository.findByUserIdAndStatusUnitId(userId, statusUnitId, pageable);
    }

    public Page<Reservations> getReservationForStatus(Long statusUnitId , Pageable pageable) {
        return reservationRepository.findByStatusUnitId(statusUnitId, pageable);
    }

    public void deleteUnit(Long id) {
        reservationRepository.deleteById(id);
    }

    public Page<Reservations> findByUnitId(Long unitId, Pageable pageable) {
        return reservationRepository.findByUnitId(unitId, pageable);
    }

    public Page<Reservations> getByStatusIdAndUnitId(Long statusId, Long unitId, Pageable pageable) {
        return reservationRepository.findByStatusIdAndUnitId(statusId, unitId, pageable);
    }

    public void setCommissionForAllReservations(Double commission) {
        List<Reservations> reservations = reservationRepository.findAll();
        for (Reservations reservation : reservations) {
            reservation.setCommision(commission);
        }
        reservationRepository.saveAll(reservations);
    }

    public List<Reservations> findReservationsByDepartureDateBeforeAndUserIdAndNotEvaluating(LocalDate date, Long userId){
        return reservationRepository.findReservationsByDepartureDateBeforeAndUserIdAndNotEvaluating(date, userId);
    }

    public Page<Reservations> getUserReservationsLesserArrivalDate(Long userId, Pageable pageable) {

        LocalDate currentDate = LocalDate.now();
        return reservationRepository.findUserReservationsWithStatusAndLesserArrivalDate(userId, currentDate, pageable);
    }

    public Page<Reservations> getUserReservationsGreaterArrivalDate(Long userId, Pageable pageable) {

        LocalDate currentDate = LocalDate.now();
        return reservationRepository.findUserReservationsWithStatusAndGreaterArrivalDate(userId, currentDate, pageable);
    }

    public List<ReserveDateHalls> getByUnitId(Long unitId) {
        return reserveDateHallsRepository.findListByUnitId(unitId); // Correctly reference the instance
    }

}
