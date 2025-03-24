package com.AlTaraf.Booking.support.advice;

import com.AlTaraf.Booking.database.entity.ErrorLog;
import com.AlTaraf.Booking.response.ErrorResponse;
import com.AlTaraf.Booking.response.base.BaseResponse;
import com.AlTaraf.Booking.response.keys.ResponseKeys;
import com.AlTaraf.Booking.service.ErrorLogService;
import com.AlTaraf.Booking.support.advice.base.BaseAdvice;
import com.AlTaraf.Booking.support.utils.UserUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;

@ControllerAdvice
public class ExceptionAdvice extends BaseAdvice implements Serializable {

    private static final long serialVersionUID = -7417021579458169651L;
//    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);


    public ExceptionAdvice(UserUtils securityUtils, ErrorLogService errorLogService) {
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
