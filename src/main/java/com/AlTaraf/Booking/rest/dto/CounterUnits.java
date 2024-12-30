package com.AlTaraf.Booking.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CounterUnits {

    private long counterAllResidencies;

    private long counterAllHotel;

    private long counterAllHotelPartment;

    private long counterAllExternalPartment;

    private long counterResort;

    private long counterChalet;

    private long counterlounge;
}
