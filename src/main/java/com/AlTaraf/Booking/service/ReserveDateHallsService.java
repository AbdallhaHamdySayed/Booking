package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.database.entity.DateInfoHalls;
import com.AlTaraf.Booking.database.entity.ReserveDateHalls;
import com.AlTaraf.Booking.database.repository.ReserveDateHallsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReserveDateHallsService {

    @Autowired
    ReserveDateHallsRepository reserveDateHallsRepository;

    @Autowired
    DateInfoHallsService dateInfoHallsService;

    public ReserveDateHalls save( ReserveDateHalls reserveDateHalls ) {
        return reserveDateHallsRepository.save(reserveDateHalls);
    }

    public void delete( ReserveDateHalls reserveDateHalls ) {
        reserveDateHallsRepository.delete(reserveDateHalls);
    }

    public List<ReserveDateHalls> getByUnitIdAndReserveIsTrue( Long unitId ) {
        return reserveDateHallsRepository.findByUnitIdAndReserveIsTrue(unitId);
    }

    public List<ReserveDateHalls> getByUnitId( Long unitId ) {
        return reserveDateHallsRepository.findListByUnitId(unitId);
    }

    public void deleteByUnitId( Long unitId ) {
         reserveDateHallsRepository.deleteByUnitId(unitId);
    }

    public void deleteDateInfoHallsByReserveDateHallsId( Long reserveDateHallsId ) {
        reserveDateHallsRepository.deleteDateInfoHallsByReserveDateHallsId(reserveDateHallsId);
    }

    public void deleteByReservationId ( Long reservationId ) {
        ReserveDateHalls reserveDateHalls = reserveDateHallsRepository.findByReservationId(reservationId);
        List<DateInfoHalls> dateInfoHallsList = dateInfoHallsService.getDateInfoHallsByReserveDateHallsId(reserveDateHalls.getId());

        for (DateInfoHalls dateInfoHalls: dateInfoHallsList) {
            dateInfoHallsService.delete(dateInfoHalls);
        }
        delete(reserveDateHalls);
    }


}
