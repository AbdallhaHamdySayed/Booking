package com.AlTaraf.Booking.support.exception;

import com.AlTaraf.Booking.support.exception.base.BaseException;

public class RecordIntegrityConstraintViolationException extends BaseException {

    public RecordIntegrityConstraintViolationException() {
    }

    public RecordIntegrityConstraintViolationException(Integer id) {
        super(id);
    }



    public RecordIntegrityConstraintViolationException(Throwable cause) {
        super(cause);
    }

    public RecordIntegrityConstraintViolationException(String errorCode) {
        super(errorCode);
    }

    public RecordIntegrityConstraintViolationException(String errorCode, Throwable cause) {
        super(cause);
        this.setErrorCode(errorCode);
    }
}
