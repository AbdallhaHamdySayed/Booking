package com.AlTaraf.Booking.Entity.Calender.Halls;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "DATE_INFO_HALLS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateInfoHalls {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DATE_INFO_HALLS_ID")
    private Long id;

    @Column(name = "DATE")
    private Date date;

    @Column(name = "IS_EVENING")
    private boolean isEvening;

    @Column(name = "IS_MORNING")
    private boolean isMorning;

    @ManyToOne
    @JoinColumn(name = "RESERVE_DATE_HALLS_ID")
    @JsonBackReference
    private ReserveDateHalls reserveDateHalls;
}
