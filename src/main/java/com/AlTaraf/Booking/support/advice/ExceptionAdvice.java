package com.AlTaraf.Booking.support.advice;

import com.zad.altamim.service.database.entity.ErrorLog;
import com.zad.altamim.service.response.ErrorResponse;
import com.zad.altamim.service.response.base.BaseResponse;
import com.zad.altamim.service.response.keys.ResponseKeys;
import com.zad.altamim.service.service.ErrorLogService;
import com.zad.altamim.service.support.advice.base.BaseAdvice;
import com.zad.altamim.service.support.utils.SecurityUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;

@ControllerAdvice
public class ExceptionAdvice extends BaseAdvice implements Serializable {

    private static final long serialVersionUID = -7417021579458169651L;
//    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);


    public ExceptionAdvice(SecurityUtils securityUtils, ErrorLogService errorLogService) {
        super(securityUtils, errorLogService);
    }

//    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseResponse handleError(Exception e) {
        e.printStackTrace();
        ErrorLog errorLog = super.saveLog(e);
        return new ErrorResponse(ResponseKeys.SYSTEM_HAVE_ISSUE, errorLog.getId());
    }

}
