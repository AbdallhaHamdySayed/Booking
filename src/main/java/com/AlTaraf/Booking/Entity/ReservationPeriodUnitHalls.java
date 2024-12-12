package com.AlTaraf.Booking.Entity;

import com.AlTaraf.Booking.Entity.Reservation.Reservations;
import com.AlTaraf.Booking.Entity.unit.AvailablePeriods.AvailablePeriods;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "reservation_periods_unit_halls")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationPeriodUnitHalls {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservations reservations;

    @ManyToOne
    @JoinColumn(name = "available_periods_id")
    private AvailablePeriods availablePeriods;

}
