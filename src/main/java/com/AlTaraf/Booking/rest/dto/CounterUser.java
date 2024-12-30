package com.AlTaraf.Booking.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CounterUser {

    private Long counterAllUsers;

    private Long counterUserGuest;

    private Long counterUserLessor;
}
