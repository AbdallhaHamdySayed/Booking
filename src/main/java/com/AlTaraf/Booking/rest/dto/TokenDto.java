package com.AlTaraf.Booking.rest.dto;

import com.AlTaraf.Booking.Security.token.TokenType;
import com.AlTaraf.Booking.rest.dto.base.BaseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;

import java.time.LocalDateTime;

/**
 * DTO for {@link com.zad.altamim.service.security.token.Token}
 */
@Value
public class TokenDto extends BaseDto<Integer> {
    String token;
    TokenType tokenType;
    boolean revoked;
    boolean expired;
    public String operation;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdAt;

}