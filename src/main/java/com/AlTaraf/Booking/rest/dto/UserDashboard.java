package com.AlTaraf.Booking.rest.dto;


import com.AlTaraf.Booking.database.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDashboard {
    private Long userId;
    private String username;
    private String phone;
    private String email;
    private Set<Role> roles = new HashSet<>();
    private CityDtoSample city;
    private Boolean ban;
    private List<Boolean> warnings = Arrays.asList(false, false, false);
}
