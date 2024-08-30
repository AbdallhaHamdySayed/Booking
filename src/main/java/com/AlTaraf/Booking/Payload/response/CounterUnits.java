package com.AlTaraf.Booking.Payload.response;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity
//@Table(name = "COUNTER_UNIT")
public class CounterUnits {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

//    @Column(name = "COUNTER_ALL_RESIDENCIES")
    private long counterAllResidencies;

//    @Column(name = "COUNTER_ALL_HOTEL")
    private long counterAllHotel;

//    @Column(name = "COUNTER_ALL_HOTEL_PARTMENT")
    private long counterAllHotelPartment;

//    @Column(name = "COUNTER_ALL_EXTERNAL_PARTMENT")
    private long counterAllExternalPartment;

//    @Column(name = "COUNTER_RESORT")
    private long counterResort;

//    @Column(name = "COUNTER_CHALET")
    private long counterChalet;

//    @Column(name = "COUNTER_LOUNGE")
    private long counterlounge;
}
