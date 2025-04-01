package com.AlTaraf.Booking.support.exception;

import com.AlTaraf.Booking.support.exception.base.BaseException;
import lombok.Data;

@Data
public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException(Integer id) {
        super(id);
    }
}
