package com.AlTaraf.Booking.Security.jwt;

import com.AlTaraf.Booking.response.ErrorResponse;
import com.AlTaraf.Booking.response.keys.ErrorStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class InvalidJWTToken {


    public void sendErrorResponse(HttpServletResponse response, ErrorStatus errorStatus) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8"); // Ensure UTF-8 encoding

        ErrorResponse errorResponse = new ErrorResponse(errorStatus.getErrorKey());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }


}
