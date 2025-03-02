package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.database.entity.DateInfo;
import com.AlTaraf.Booking.database.repository.DateInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DateInfoService {

    @Autowired
    DateInfoRepository dateInfoRepository;

    public List<DateInfo> getDateInfoByReserveDateUnitId(Long reserveDateUnitId ) {
        return dateInfoRepository.findByReserveDateUnitId(reserveDateUnitId);
    }

    public void deleteDateInfo(DateInfo dateInfo) {
        dateInfoRepository.delete(dateInfo);
    }
}
