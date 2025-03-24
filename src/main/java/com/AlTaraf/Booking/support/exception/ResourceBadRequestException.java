package com.AlTaraf.Booking.support.exception;

import com.AlTaraf.Booking.support.exception.base.BaseException;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceBadRequestException extends BaseException {

    private String message;

    public ResourceBadRequestException(Integer id , String code, String message) {
        super(id, code);
        setMessage(message);
    }
}
