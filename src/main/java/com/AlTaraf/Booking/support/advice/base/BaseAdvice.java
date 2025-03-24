package com.AlTaraf.Booking.support.advice.base;

import com.zad.altamim.service.database.entity.ErrorLog;
import com.zad.altamim.service.service.ErrorLogService;
import com.zad.altamim.service.support.utils.DateUtils;
import com.zad.altamim.service.support.utils.SecurityUtils;
import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class BaseAdvice implements Serializable {

    private static final long serialVersionUID = 4783057014277021248L;

    private SecurityUtils securityUtils;
    private ErrorLogService errorLogService;

    public ErrorLog saveLog(Throwable throwable) {
        ErrorLog errorLog = new ErrorLog();
        errorLog.setErrorDate(DateUtils.getCurrentDate());
        errorLog.setExceptionTrace(getStackTraceFromException(throwable));
        errorLog.setLoginUser(securityUtils.getLoggedInUser().getId() + "");
        return errorLogService.createEntity(errorLog);
    }

    public ErrorLog saveLog(String requestedBody, Throwable throwable) {
        ErrorLog errorLog = new ErrorLog();
        errorLog.setErrorDate(DateUtils.getCurrentDate());
        errorLog.setExceptionTrace(getStackTraceFromException(throwable));
        errorLog.setLoginUser(securityUtils.getLoggedInUser().getId() + "");
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
