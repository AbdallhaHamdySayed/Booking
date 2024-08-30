package com.AlTaraf.Booking.Dto.User;



import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.CityDtoSample;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRegisterDto {
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private CityDtoSample city;
    private Set<String> roles;
}
