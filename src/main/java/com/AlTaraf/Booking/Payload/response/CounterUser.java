package com.AlTaraf.Booking.Payload.response;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity
//@Table(name = "COUNTER_USER")
public class CounterUser {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

//    @Column(name = "COUNTER_ALL_USERS")
    private Long counterAllUsers;

//    @Column(name = "COUNTER_USER_GUEST")
    private Long counterUserGuest;

//    @Column(name = "COUNTER_USER_LESSOR")
    private Long counterUserLessor;
}
