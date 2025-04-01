package com.AlTaraf.Booking.support.advice;

import com.AlTaraf.Booking.database.entity.ErrorLog;
import com.AlTaraf.Booking.response.ErrorResponse;
import com.AlTaraf.Booking.response.base.BaseResponse;
import com.AlTaraf.Booking.response.keys.ResponseKeys;
import com.AlTaraf.Booking.service.ErrorLogService;
import com.AlTaraf.Booking.support.advice.base.BaseAdvice;
import com.AlTaraf.Booking.support.utils.UserUtils;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;

@ControllerAdvice
public class InvalidFormatExceptionAdvice extends BaseAdvice implements Serializable {

    private static final long serialVersionUID = -3315137559919011428L;
//    private static final Logger logger = LoggerFactory.getLogger(InvalidFormatExceptionAdvice.class);

    public InvalidFormatExceptionAdvice(UserUtils securityUtils, ErrorLogService errorLogService) {
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
