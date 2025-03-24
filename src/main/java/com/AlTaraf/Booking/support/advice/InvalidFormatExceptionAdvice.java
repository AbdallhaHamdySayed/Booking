package com.AlTaraf.Booking.support.advice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.zad.altamim.service.database.entity.ErrorLog;
import com.zad.altamim.service.response.ErrorResponse;
import com.zad.altamim.service.response.base.BaseResponse;
import com.zad.altamim.service.response.keys.ResponseKeys;
import com.zad.altamim.service.service.ErrorLogService;
import com.zad.altamim.service.support.advice.base.BaseAdvice;
import com.zad.altamim.service.support.utils.SecurityUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;

@ControllerAdvice
public class InvalidFormatExceptionAdvice extends BaseAdvice implements Serializable {

    private static final long serialVersionUID = -3315137559919011428L;
//    private static final Logger logger = LoggerFactory.getLogger(InvalidFormatExceptionAdvice.class);

    public InvalidFormatExceptionAdvice(SecurityUtils securityUtils, ErrorLogService errorLogService) {
        super(securityUtils, errorLogService);
    }

    @ExceptionHandler(value = {InvalidFormatException.class})
    @ResponseBody
    public BaseResponse handleError(InvalidFormatException e) {
//        logger.error("Error Log StackTrace ::>>", e.getStackTrace());
        e.printStackTrace();
//        logger.error("Error Log Cause ::>>", e.getCause());
        ErrorLog errorLog = super.saveLog(e.getCause());
        return new ErrorResponse(ResponseKeys.HAVE_RELATED_DATA, errorLog.getId());
    }


}
