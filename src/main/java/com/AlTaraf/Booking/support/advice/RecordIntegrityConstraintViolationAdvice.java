package com.AlTaraf.Booking.support.advice;

import com.zad.altamim.service.database.entity.ErrorLog;
import com.zad.altamim.service.response.ErrorResponse;
import com.zad.altamim.service.response.base.BaseResponse;
import com.zad.altamim.service.response.keys.ResponseKeys;
import com.zad.altamim.service.service.ErrorLogService;
import com.zad.altamim.service.support.advice.base.BaseAdvice;
import com.zad.altamim.service.support.exception.RecordIntegrityConstraintViolationException;
import com.zad.altamim.service.support.utils.SecurityUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;

@ControllerAdvice
public class RecordIntegrityConstraintViolationAdvice extends BaseAdvice implements Serializable {
    private static final long serialVersionUID = -4188022001736243871L;
//    private static final Logger logger = LoggerFactory.getLogger(RecordIntegrityConstraintViolationAdvice.class);

    public RecordIntegrityConstraintViolationAdvice(SecurityUtils securityUtils, ErrorLogService errorLogService) {
        super(securityUtils, errorLogService);
    }

    @ExceptionHandler(value = {RecordIntegrityConstraintViolationException.class})
    @ResponseBody
    public BaseResponse handleError(RecordIntegrityConstraintViolationException e) {
//        logger.error("Error Log StackTrace ::>>", e.getStackTrace());
        e.printStackTrace();
//        logger.error("Error Log Cause ::>>", e.getCause());
        ErrorLog errorLog = super.saveLog(e.getCause());
        return new ErrorResponse(ResponseKeys.HAVE_RELATED_DATA, errorLog.getId());
    }
//    @ExceptionHandler(value = {RecordIntegrityConstraintViolationException.class, DataIntegrityViolationException.class, SQLIntegrityConstraintViolationException.class})

}
