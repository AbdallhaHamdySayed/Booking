package com.AlTaraf.Booking.Entity.unit.roomAvailable;

import com.AlTaraf.Booking.Entity.unit.Unit;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "room_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROOM_DETAILS_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ROOM_AVAILABLE_ID")
    private RoomAvailable roomAvailable;

    @Column(name = "ROOM_NUMBER")
    private int roomNumber;

   @Column(name = "NEW_PRICE")
   private Integer newPrice;

   @Column(name = "OLD_PRICE")
   private Integer oldPrice;

    @Column(name = "ADULTS_ALLOWED")
    private int adultsAllowed;

    @Column(name = "CHILDREN_ALLOWED")
    private int childrenAllowed;

    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = false)
    @JsonBackReference
    private Unit unit;
}
