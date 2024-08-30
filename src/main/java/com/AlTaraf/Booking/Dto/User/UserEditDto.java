package com.AlTaraf.Booking.Dto.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEditDto {
    private Long id;
    private String username;
    private String email;
    private String phone;
}