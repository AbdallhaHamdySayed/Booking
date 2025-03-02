package com.AlTaraf.Booking.database.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "RESERVE_DATE_UNIT")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveDateUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESERVE_DATE_UNIT_ID")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "RESERVE_DATE_UNIT_ID", referencedColumnName = "RESERVE_DATE_UNIT_ID")
    @JsonManagedReference
    private List<DateInfo> dateInfoList;

    @ManyToOne
    @JoinColumn(name = "UNIT_ID")
    private Unit unit;

    @ManyToOne
    @JoinColumn(name = "RESERVATION_ID")
    private Reservations reservations;
}
