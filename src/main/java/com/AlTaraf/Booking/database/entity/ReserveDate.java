package com.AlTaraf.Booking.database.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "RESERVE_DATE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESERVE_DATE_ID")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "RESERVE_DATE_ID", referencedColumnName = "RESERVE_DATE_ID")
    @JsonManagedReference
    private List<DateInfo> dateInfoList;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ROOM_DETAILS_FOR_AVAILABLE_AREA_ID")
    private RoomDetailsForAvailableArea roomDetailsForAvailableArea;

    @ManyToOne
    @JoinColumn(name = "UNIT_ID")
    private Unit unit;

}
