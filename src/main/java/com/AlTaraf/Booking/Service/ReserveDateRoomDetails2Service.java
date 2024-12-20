package com.AlTaraf.Booking.Service;

import com.AlTaraf.Booking.Entity.Calender.ReserveDateRoomDetails2;
import com.AlTaraf.Booking.Repository.ReserveDateRoomDetails2Repository;
import com.AlTaraf.Booking.Service.base.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ReserveDateRoomDetails2Service extends BaseService<Integer, ReserveDateRoomDetails2> {

    private final ReserveDateRoomDetails2Repository reserveDateRoomDetails2Repository;

    @Override
    public ReserveDateRoomDetails2Repository getRepository() {
        return reserveDateRoomDetails2Repository;
    }

    public List<ReserveDateRoomDetails2> listAll () {
        return reserveDateRoomDetails2Repository.findAll();
    }

    public void delete(ReserveDateRoomDetails2 reserveDateRoomDetails2) {
        reserveDateRoomDetails2Repository.delete(reserveDateRoomDetails2);
    }
}
