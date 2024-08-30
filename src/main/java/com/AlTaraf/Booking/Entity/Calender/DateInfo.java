package com.AlTaraf.Booking.Entity.Calender;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "DATE_INFO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DATE_INFO_ID")
    private Long id;

    @Column(name = "DATE")
    private Date date;


    @ManyToOne
    @JoinColumn(name = "RESERVE_DATE_ID")
    @JsonBackReference
    private ReserveDate reserveDate;

    @ManyToOne
    @JoinColumn(name = "RESERVE_DATE_ROOM_DETAILS_ID")
    @JsonBackReference
    private ReserveDateRoomDetails reserveDateRoomDetails;

    @ManyToOne
    @JoinColumn(name = "RESERVE_DATE_UNIT_ID")
    @JsonBackReference
    private ReserveDateUnit reserveDateUnit;
}
