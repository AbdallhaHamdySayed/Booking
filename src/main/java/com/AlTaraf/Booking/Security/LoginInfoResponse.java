package com.AlTaraf.Booking.Security;

import com.AlTaraf.Booking.rest.dto.UserDto;
import com.AlTaraf.Booking.rest.dto.base.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginInfoResponse extends BaseDto<Integer> {
    private UserDto user;
    private List<String> authorities;

}