package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.database.entity.*;
import com.AlTaraf.Booking.database.repository.*;
import com.AlTaraf.Booking.rest.dto.PushNotificationRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    ReserveDateHallsRepository reserveDateHallsRepository;

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
    ReserveDateUnitService reserveDateUnitService;

    @Autowired
    DateInfoRepository dateInfoRepository;

    @Autowired
    DateInfoHallsService dateInfoHallsService;

    @Autowired
    ReservationPeriodUnitHallsService reservationPeriodUnitHallsService;

    @Autowired
    UserService userService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    NotificationService notificationService;

    @Autowired
    ReserveDateHallsService reserveDateHallsService;

    public Reservations saveReservation(Reservations reservations) {
        return reservationRepository.save(reservations);
    }

    public Reservations getReservationById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    public Unit findUnitByReservationId(Long reservationId){
        return reservationRepository.findUnitByReservationId(reservationId);
    }

    public void acceptReservation(Long reservationId, Long statusUnitId) {

        Reservations reservations = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found with id: " + reservationId));

        LocalDate dateOfArrival = reservations.getDateOfArrival();

        LocalDate departureDate = reservations.getDepartureDate();

        Unit unit = findUnitByReservationId(reservationId);

        if ( unit.getUnitType().getId() == 2 ) {

            ReserveDateHalls reserveDateHall = new ReserveDateHalls();
            reserveDateHall.setUnit(unit);
            reserveDateHall.setReservations(reservations);
            reserveDateHallsRepository.save(reserveDateHall);

            DateInfoHalls dateInfoHalls = new DateInfoHalls();
            dateInfoHalls.setReserveDateHalls(reserveDateHall);
            dateInfoHalls.setDate(dateOfArrival);
            dateInfoHallsService.save(dateInfoHalls);

            ReservationPeriodUnitHalls reservationPeriodUnitHalls = reservationPeriodUnitHallsService.getByReservationId(reservationId);
            System.out.println("reservationPeriodUnitHalls getAvailablePeriods: " + reservationPeriodUnitHalls.getAvailablePeriods().getId());
            reservationPeriodUnitHalls.setStatusUnit(statusRepository.findById(statusUnitId).orElse(null));
            reservationPeriodUnitHallsService.save(reservationPeriodUnitHalls);

            if (reservationPeriodUnitHalls.getAvailablePeriods().getId() == 2) {
                dateInfoHalls.setEvening(true);
            } else if (reservationPeriodUnitHalls.getAvailablePeriods().getId() == 1) {
                dateInfoHalls.setMorning(true);
            }
            dateInfoHallsService.save(dateInfoHalls);

            if (unit.getPeriodCount() == 2) {
                System.out.println("unit.getPeriodCount() == 2");

                List<Reservations> reservationsList = reservationRepository.findReservationsByDate(unit.getId(), dateOfArrival, departureDate);

                for (Reservations reservation : reservationsList) {
                    List<ReservationPeriodUnitHalls> reservationPeriodUnitHallsListByUnitMorningAndAccepted = reservationPeriodUnitHallsService.getByUnitIdAndAvailableAndAccepted(unit.getId(), 1L);
                    List<ReservationPeriodUnitHalls> reservationPeriodUnitHallsListByUnitMorningAndPended = reservationPeriodUnitHallsService.getByUnitIdAndAvailableAndPended(unit.getId(), 1L);
                    System.out.println("reservation: " + reservation.getId());
                    System.out.println("reservationPeriodUnitHallsListByUnitMorning.size(): " + reservationPeriodUnitHallsListByUnitMorningAndAccepted.size());

                    if (!reservationPeriodUnitHallsListByUnitMorningAndAccepted.isEmpty()) {
                        System.out.println("!reservationPeriodUnitHallsListByUnitMorning.isEmpty()");
                        for (ReservationPeriodUnitHalls reservationPeriodUnitHalls1 : reservationPeriodUnitHallsListByUnitMorningAndPended) {
                            reservationPeriodUnitHalls1.setStatusUnit(statusRepository.findById(3L).orElse(null));
                            reservationPeriodUnitHallsService.save(reservationPeriodUnitHalls1);
                            reservationPeriodUnitHalls1.getReservations().setStatusUnit(statusRepository.findById(3L).orElse(null));
                            reservationRepository.save(reservationPeriodUnitHalls1.getReservations());
                        }
                    }

                    List<ReservationPeriodUnitHalls> reservationPeriodUnitHallsListByUnitEveningAndAccepted = reservationPeriodUnitHallsService.getByUnitIdAndAvailableAndAccepted(unit.getId(), 2L);
                    List<ReservationPeriodUnitHalls> reservationPeriodUnitHallsListByUnitEveningAndPended = reservationPeriodUnitHallsService.getByUnitIdAndAvailableAndPended(unit.getId(), 2L);
                    System.out.println("reservationPeriodUnitHallsListByUnitEvening.size(): " + reservationPeriodUnitHallsListByUnitEveningAndAccepted.size());

                    if (!reservationPeriodUnitHallsListByUnitEveningAndAccepted.isEmpty()) {
                        System.out.println("!reservationPeriodUnitHallsListByUnitEvening.isEmpty()");
                        for (ReservationPeriodUnitHalls reservationPeriodUnitHalls2 : reservationPeriodUnitHallsListByUnitEveningAndPended) {
                            reservationPeriodUnitHalls2.setStatusUnit(statusRepository.findById(3L).orElse(null));
                            reservationPeriodUnitHallsService.save(reservationPeriodUnitHalls2);
                            reservationPeriodUnitHalls2.getReservations().setStatusUnit(statusRepository.findById(3L).orElse(null));
                            reservationRepository.save(reservationPeriodUnitHalls2.getReservations());
                        }
                    }
                }

            }
            else if (unit.getPeriodCount() == 1) {
                System.out.println("*********************** unit.getPeriodCount() == 1");
                List<Reservations> reservationsList = reservationRepository.findReservationsByDate(unit.getId(),dateOfArrival, departureDate);
                System.out.println("reservationsList size: " + reservationsList.size());
                for (Reservations reservation : reservationsList) {
                        System.out.println("reservation: " + reservation.getId());
                        reservation.setStatusUnit(statusRepository.findById(3L).orElse(null));
                        System.out.println("------------------------------------------------------");
                        reservationRepository.save(reservation);
                        reservationPeriodUnitHalls.setStatusUnit(statusRepository.findById(3L).orElse(null));
                    reservationPeriodUnitHallsService.save(reservationPeriodUnitHalls);
                    }
                }
            }

        if (unit.getAccommodationType() != null) {

            if ( unit.getAccommodationType().getId() == 4 || unit.getAccommodationType().getId() == 6 ||
                    unit.getAccommodationType().getId() == 3 ) {
                ReserveDateUnit reserveDateUnit = new ReserveDateUnit();
                reserveDateUnit.setUnit(unit);
                reserveDateUnit.setReservations(reservations);
                reserveDateUnitService.save(reserveDateUnit);

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
        System.out.println("user.getId(): " + user.getId());

        if (user.getWallet() > 0) {
            System.out.println("user.getWallet() > 0 ");

            userService.addingWallet(user, reservations.getCommision());

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

        if ( Optional.ofNullable(unit.getAccommodationType())
                        .map(accommodationType ->
                                accommodationType.getId() == 4 ||
                                        accommodationType.getId() == 6 ||
                                        accommodationType.getId() == 3)
                        .orElse(false) ) {

            System.out.println("**************************************************************************************************");
            List<Reservations> reservationsLists = reservationRepository.findReservationsByCriteria(unit.getUser().getId(), dateOfArrival, departureDate);
            System.out.println("userId: " + unit.getUser().getId());
            System.out.println("dateOfArrival: " + dateOfArrival);
            System.out.println("departureDate: " + departureDate);

            for (Reservations reservation : reservationsLists) {
                reservation.setStatusUnit(statusRepository.findById(3L).orElse(null));
                System.out.println("------------------------------------------------------");
                System.out.println("reservation: " + reservation.getId());
                reservationRepository.save(reservation);
            }
        }
    }

    public void rejectReservation(Long reservationId, Long statusUnitId) throws IOException, InterruptedException {
        StatusUnit statusUnit = statusRepository.findById(statusUnitId)
                .orElseThrow(() -> new EntityNotFoundException("StatusUnit not found with id: " + statusUnitId));
        Reservations reservations = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found with id: " + reservationId));

        reservations.setStatusUnit(statusUnit);

        reservationRepository.save(reservations);

        notificationService.pushNotificationsReservationRejected(reservations.getId());

    }

    public void cancelReservation(Long reservationId) throws IOException, InterruptedException {

        Reservations reservations = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found with id: " + reservationId));

        Unit unit = findUnitByReservationId(reservationId);


        if ( reservations.getUnit().getUnitType().getId() == 2 ) {

            reserveDateHallsService.deleteByReservationId(reservations.getId());

            reservationPeriodUnitHallsService.deleteByReservationId(reservations.getId());

        }

        else if ( unit.getAccommodationType().getId() == 4 || unit.getAccommodationType().getId() == 6 ||
                unit.getAccommodationType().getId() == 3 ) {

            reserveDateUnitService.deleteByReservationId(reservations.getId());

        }

        userService.subtractWallet(reservations.getUser(), reservations.getCommision());

        updateStatueReservation(reservations.getId(), 4L);

        notificationService.pushNotificationsReservationCanceled(reservations.getId());

        notificationService.pushNotificationsRecoveryWallet(reservations.getId());

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


    public Page<Reservations> getByStatusIdAndUnitId(Long statusId, List<Unit> units, Pageable pageable) {
        return reservationRepository.getByStatusIdAndUnits(statusId, units, pageable);
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

    public void updateStatueReservation(Long reservationId, Long statusUnitId) {

        Reservations reservations = reservationRepository.findById(reservationId).orElse(null);
        StatusUnit statusUnit = statusRepository.findById(statusUnitId).orElse(null);
        reservations.setStatusUnit(statusUnit);
        reservationRepository.save(reservations);
    }

}
