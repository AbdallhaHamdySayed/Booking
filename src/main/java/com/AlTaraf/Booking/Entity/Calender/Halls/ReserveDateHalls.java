package com.AlTaraf.Booking.Entity.Calender.Halls;

import com.AlTaraf.Booking.Entity.unit.Unit;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "RESERVE_DATE_HALLS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveDateHalls {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESERVE_DATE_HALLS_ID")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "RESERVE_DATE_HALLS_ID", referencedColumnName = "RESERVE_DATE_HALLS_ID")
    @JsonManagedReference
    private List<DateInfoHalls> dateInfoList;

    @ManyToOne
    @JoinColumn(name = "UNIT_ID")
    private Unit unit;;

    @Column(name = "RESERVE")
    private Boolean reserve;
}
