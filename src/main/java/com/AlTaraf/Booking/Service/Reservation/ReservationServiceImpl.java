package com.AlTaraf.Booking.Service.Reservation;

import com.AlTaraf.Booking.Entity.Calender.Halls.ReserveDateHalls;
import com.AlTaraf.Booking.Entity.Reservation.Reservations;
import com.AlTaraf.Booking.Entity.Transactions.TotalTransactions;
import com.AlTaraf.Booking.Entity.Transactions.Transactions;
import com.AlTaraf.Booking.Entity.Transactions.TransactionsDetail;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.Wallet.Wallet;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Entity.unit.availableArea.AvailableArea;
import com.AlTaraf.Booking.Entity.unit.availableArea.RoomDetailsForAvailableArea;
import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomAvailable;
import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomDetails;
import com.AlTaraf.Booking.Entity.unit.statusUnit.StatusUnit;
import com.AlTaraf.Booking.Repository.Reservation.ReservationRepository;
import com.AlTaraf.Booking.Repository.ReserveDateRepository.ReserveDateHallsRepository;
import com.AlTaraf.Booking.Repository.Transactions.TotalTransactionsRepository;
import com.AlTaraf.Booking.Repository.Transactions.TransactionsDetailRepository;
import com.AlTaraf.Booking.Repository.Transactions.TransactionsRepository;
import com.AlTaraf.Booking.Repository.Wallet.WalletRepository;
import com.AlTaraf.Booking.Repository.unit.RoomDetails.RoomDetailsForAvailableAreaRepository;
import com.AlTaraf.Booking.Repository.unit.RoomDetails.RoomDetailsRepository;
import com.AlTaraf.Booking.Repository.unit.statusUnit.StatusRepository;
import com.AlTaraf.Booking.Repository.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

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

    @Override
    public Reservations saveReservation(Reservations reservations) {
        return reservationRepository.save(reservations);
    }

    @Override
    public Reservations getReservationById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    @Override
    public Unit findUnitByReservationId(Long reservationId){
        return reservationRepository.findUnitByReservationId(reservationId);
    }

    @Override
    public void updateStatusForReservation(Long reservationId, Long statusUnitId) {

        Reservations reservations = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found with id: " + reservationId));

        Unit unit = findUnitByReservationId(reservationId);

        if ( (unit.getUnitType().getId() == 2 || unit.getAccommodationType().getId() == 4 ||
                unit.getAccommodationType().getId() == 6 ) && statusUnitId.equals(2L) ) {
            List<ReserveDateHalls> reserveDateHalls = reserveDateHallsRepository.findByUnitId(unit.getId());

            for ( ReserveDateHalls reserve: reserveDateHalls ) {
                reserve.setReserve(true);
            }

        }

        if ( statusUnitId.equals(2L) && getAvailableAreaByReservations(reservationId) != null ) {

            AvailableArea availableArea = getAvailableAreaByReservations(reservationId);
            RoomDetailsForAvailableArea roomDetailsForAvailableArea = roomDetailsForAvailableAreaRepository.findByUnitIdAndAvailableAreaId(unit.getId(), availableArea.getId());


            int numberRoom = roomDetailsForAvailableArea.getRoomNumber();
            if (numberRoom > 0) {
                numberRoom--;
                roomDetailsForAvailableArea.setRoomNumber(numberRoom);
            }

            if (numberRoom == 0) {

                Integer roomAvailableCount = unit.getRoomAvailableCount();
                roomAvailableCount--;
                unit.setRoomAvailableCount(roomAvailableCount);

                System.out.println("roomAvailableCount: " + roomAvailableCount);

            }
        }

        if ( statusUnitId.equals(2L) && getRoomAvailableByReservations(reservationId) != null ) {

            RoomAvailable roomAvailable = getRoomAvailableByReservations(reservationId);
            RoomDetails roomDetails = roomDetailsRepository.findRoomDetailsByUnitIdAndRoomAvailableId(unit.getId(), roomAvailable.getId());


            int numberRoom = roomDetails.getRoomNumber();
            if (numberRoom > 0) {
                numberRoom--;
                roomDetails.setRoomNumber(numberRoom);
            }

            if (numberRoom == 0) {

                Integer roomAvailableCount = unit.getRoomAvailableCount();
                roomAvailableCount--;
                unit.setRoomAvailableCount(roomAvailableCount);

                System.out.println("roomAvailableCount: " + roomAvailableCount);
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

    @Override
    public AvailableArea getAvailableAreaByReservations(Long reservationId) {
        return reservationRepository.findAvailableAreaIdByReservationId(reservationId);
    }

    @Override
    public RoomAvailable getRoomAvailableByReservations(Long reservationId) {
        return reservationRepository.findRoomAvailableIdByReservationId(reservationId);
    }

    @Override
    public Page<Reservations> getReservationForUserAndStatus(Long userId, Long statusUnitId , Pageable pageable) {
        return reservationRepository.findByUserIdAndStatusUnitId(userId, statusUnitId, pageable);
    }

    @Override
    public Page<Reservations> getReservationForStatus(Long statusUnitId , Pageable pageable) {
        return reservationRepository.findByStatusUnitId(statusUnitId, pageable);
    }

    @Override
    public void deleteUnit(Long id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public Page<Reservations> findByUnitId(Long unitId, Pageable pageable) {
        return reservationRepository.findByUnitId(unitId, pageable);
    }

    @Override
    public Page<Reservations> getByStatusIdAndUnitId(Long statusId, Long unitId, Pageable pageable) {
        return reservationRepository.findByStatusIdAndUnitId(statusId, unitId, pageable);
    }

    @Override
    public void setCommissionForAllReservations(Double commission) {
        List<Reservations> reservations = reservationRepository.findAll();
        for (Reservations reservation : reservations) {
            reservation.setCommision(commission);
        }
        reservationRepository.saveAll(reservations);
    }

    @Override
    public List<Reservations> findReservationsByDepartureDateBeforeAndUserIdAndNotEvaluating(LocalDate date, Long userId){
        return reservationRepository.findReservationsByDepartureDateBeforeAndUserIdAndNotEvaluating(date, userId);
    }

    @Override
    public Page<Reservations> getUserReservationsLesserArrivalDate(Long userId, Pageable pageable) {

        LocalDate currentDate = LocalDate.now();
        return reservationRepository.findUserReservationsWithStatusAndLesserArrivalDate(userId, currentDate, pageable);
    }

    @Override
    public Page<Reservations> getUserReservationsGreaterArrivalDate(Long userId, Pageable pageable) {

        LocalDate currentDate = LocalDate.now();
        return reservationRepository.findUserReservationsWithStatusAndGreaterArrivalDate(userId, currentDate, pageable);
    }


}

