package com.AlTaraf.Booking.support.advice.base;


import com.AlTaraf.Booking.database.entity.ErrorLog;
import com.AlTaraf.Booking.service.ErrorLogService;
import com.AlTaraf.Booking.support.utils.DateUtils;
import com.AlTaraf.Booking.support.utils.UserUtils;
import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class BaseAdvice implements Serializable {

    private static final long serialVersionUID = 4783057014277021248L;

    private UserUtils userUtils;
    private ErrorLogService errorLogService;

    public ErrorLog saveLog(Throwable throwable) {
        ErrorLog errorLog = new ErrorLog();
        errorLog.setErrorDate(DateUtils.getCurrentDate());
        errorLog.setExceptionTrace(getStackTraceFromException(throwable));
        errorLog.setLoginUser(userUtils.getLoggedInUser().getId() + "");
        return errorLogService.createEntity(errorLog);
    }

    public ErrorLog saveLog(String requestedBody, Throwable throwable) {
        ErrorLog errorLog = new ErrorLog();
        errorLog.setErrorDate(DateUtils.getCurrentDate());
        errorLog.setExceptionTrace(getStackTraceFromException(throwable));
        errorLog.setLoginUser(userUtils.getLoggedInUser().getId() + "");
        errorLog.setRequestedPayload(requestedBody);
        return errorLogService.createEntity(errorLog);
    }

    private String getStackTraceFromException(Throwable throwable) {
        StackTraceElement[] elements = throwable.getStackTrace();
        String trace = null;
        for (StackTraceElement element : elements) {
            trace = (trace == null) ? element.toString() : trace + ",\n" + element.toString();
        }
        return trace;
    }

}
