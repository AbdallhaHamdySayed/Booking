package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.database.entity.DateInfo;
import com.AlTaraf.Booking.database.entity.ReserveDateUnit;
import com.AlTaraf.Booking.database.repository.ReserveDateUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReserveDateUnitService {

    @Autowired
    ReserveDateUnitRepository reserveDateUnitRepository;

    @Autowired
    DateInfoService dateInfoService;

    public ReserveDateUnit save(ReserveDateUnit reserveDateUnit) {
        return reserveDateUnitRepository.save(reserveDateUnit);
    }

    public void delete(ReserveDateUnit reserveDateUnit) {
        reserveDateUnitRepository.delete(reserveDateUnit);
    }

    public List<ReserveDateUnit> getListByUnitId( Long unitId) {
        return reserveDateUnitRepository.findListByUnitId(unitId);
    }

    public void deleteByUnitId( Long unitId ) {
        reserveDateUnitRepository.deleteByUnitId(unitId);
    }

    public void deleteDateInfoByReserveDateId( Long reserveDateId ) {
        reserveDateUnitRepository.deleteDateInfoByReserveDateId(reserveDateId);
    }

    public void deleteByReservationId ( Long reservationId ) {
        ReserveDateUnit reserveDateUnit = reserveDateUnitRepository.findByReservationId(reservationId);
        List<DateInfo> dateInfoList = dateInfoService.getDateInfoByReserveDateUnitId(reserveDateUnit.getId());
        for (DateInfo dateInfo : dateInfoList) {
            dateInfoService.deleteDateInfo(dateInfo);
        }
        delete(reserveDateUnit);
    }
}
