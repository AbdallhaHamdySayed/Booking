package com.AlTaraf.Booking.rest.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRegisterDashboardDto {
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private Set<String> roles;
}
