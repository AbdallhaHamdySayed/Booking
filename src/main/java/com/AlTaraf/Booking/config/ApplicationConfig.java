package com.AlTaraf.Booking.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    @PostConstruct
    public void init() {
        // Setting Spring Boot SetTimeZone
        TimeZone.setDefault(TimeZone.getTimeZone("Africa/Tripoli"));
        System.out.println("Spring boot application running in UTC+2 timezone: " + TimeZone.getDefault().getID());
    }

}
