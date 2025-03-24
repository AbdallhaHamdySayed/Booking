package com.AlTaraf.Booking.support.exception.base;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 3429261224926462433L;
    private Integer id;
    private String errorCode;

    public BaseException() {
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(Integer id, String errorCode) {
        this.id = id;
        this.errorCode = errorCode;
    }

    public BaseException(String errorCode) {
        this.id = id;
        this.errorCode = errorCode;
    }

    public BaseException(Integer id) {
        this.id = id;
        this.errorCode = errorCode;
    }
}
