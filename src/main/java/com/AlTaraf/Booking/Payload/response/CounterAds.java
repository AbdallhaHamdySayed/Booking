package com.AlTaraf.Booking.Payload.response;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity
//@Table(name = "COUNTER_Ads")
public class CounterAds {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

//    @Column(name = "COUNTER_ALL_ADS")
    private Long counterAllAds;

//    @Column(name = "COUNTER_ACCEPTED_ADS")
    private Long counterAcceptedAds;

//    @Column(name = "COUNTER_REJECTED_ADS")
    private Long counterRejectedAds;
}
