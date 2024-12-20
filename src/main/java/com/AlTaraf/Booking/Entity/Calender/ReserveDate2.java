package com.AlTaraf.Booking.Entity.Calender;


import com.AlTaraf.Booking.Entity.base.BaseEntity;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Entity.unit.availableArea.RoomDetailsForAvailableArea;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "RESERVE_DATE2")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveDate2 extends BaseEntity<Integer> {

    @Column(name = "DATE")
    private LocalDate date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ROOM_DETAILS_FOR_AVAILABLE_AREA_ID")
    private RoomDetailsForAvailableArea roomDetailsForAvailableArea;

    @ManyToOne
    @JoinColumn(name = "UNIT_ID")
    private Unit unit;

}
