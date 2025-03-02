package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.database.entity.ReservationPeriodUnitHalls;
import com.AlTaraf.Booking.database.repository.ReservationPeriodUnitHallsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationPeriodUnitHallsService {

    @Autowired
    ReservationPeriodUnitHallsRepository reservationPeriodUnitHallsRepository;

    public void save(ReservationPeriodUnitHalls reservationPeriodUnitHalls) {
        reservationPeriodUnitHallsRepository.save(reservationPeriodUnitHalls);
    }

    public ReservationPeriodUnitHalls getByReservationId(Long reservationId) {
        return reservationPeriodUnitHallsRepository.findByReservationId(reservationId);
    }

    public List<ReservationPeriodUnitHalls> getByUnitIdAndAvailableAndAccepted(Long unitId, Long availablePeriodsId) {
        return reservationPeriodUnitHallsRepository.findByUnitIdAndAvailableAndAccepted(unitId, availablePeriodsId);
    }

    public List<ReservationPeriodUnitHalls> getByUnitIdAndAvailableAndPended(Long unitId, Long availablePeriodsId) {
        return reservationPeriodUnitHallsRepository.findByUnitIdAndAvailableAndPended(unitId, availablePeriodsId);
    }

    public void delete(ReservationPeriodUnitHalls reservationPeriodUnitHalls) {
        reservationPeriodUnitHallsRepository.delete(reservationPeriodUnitHalls);
    }

    public void deleteByReservationId(Long reservationId) {
        delete(reservationPeriodUnitHallsRepository.findByReservationId(reservationId));
    }
}
