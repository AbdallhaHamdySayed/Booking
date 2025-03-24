package com.AlTaraf.Booking.response;

import com.AlTaraf.Booking.response.base.BaseResponse;
import com.AlTaraf.Booking.response.keys.ErrorResponsePayload;
import com.AlTaraf.Booking.response.keys.ErrorStatus;
import com.AlTaraf.Booking.support.utils.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ErrorResponse extends BaseResponse implements Serializable {

    private List<ErrorResponsePayload> errorResponsePayloadList = new ArrayList<>();
    @DateTimeFormat(style = "DD-MM-YYYY HH:MM:SS")
    private LocalDateTime timeStamp;
    private Long errorId;

    public ErrorResponse(List<String> errorCodes) {
        for (String errorKey : errorCodes) {
            this.errorResponsePayloadList.add(
                    new ErrorResponsePayload(
                            ErrorStatus.valueOf(errorKey).getErrorKey(),
                            ErrorStatus.valueOf(errorKey).getArabicMessage(),
                            ErrorStatus.valueOf(errorKey).getEnglishMessage(),
                            ErrorStatus.valueOf(errorKey).getFixAr(),
                            ErrorStatus.valueOf(errorKey).getFixEn()));
        }
        setErrorStatus(true);
        setTimeStamp(DateUtils.getCurrentDate());
    }

    public ErrorResponse(String errorKey) {
        setErrorStatus(true);
        setTimeStamp(DateUtils.getCurrentDate());
        this.errorResponsePayloadList.add(
                new ErrorResponsePayload(
                        ErrorStatus.valueOf(errorKey).getErrorKey(),
                        ErrorStatus.valueOf(errorKey).getArabicMessage(),
                        ErrorStatus.valueOf(errorKey).getEnglishMessage(),
                        ErrorStatus.valueOf(errorKey).getFixAr(),
                        ErrorStatus.valueOf(errorKey).getFixEn()));
    }

    public ErrorResponse(String errorKey, Long errorId) {
        setErrorStatus(true);
        setTimeStamp(DateUtils.getCurrentDate());
        setErrorId(errorId);
        this.errorResponsePayloadList.add(
                new ErrorResponsePayload(
                        ErrorStatus.valueOf(errorKey).getErrorKey(),
                        ErrorStatus.valueOf(errorKey).getArabicMessage(),
                        ErrorStatus.valueOf(errorKey).getEnglishMessage(),
                        ErrorStatus.valueOf(errorKey).getFixAr(),
                        ErrorStatus.valueOf(errorKey).getFixEn()));
    }


    public ErrorResponse() {
        setErrorStatus(false);
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Long getErrorId() {
        return errorId;
    }

    public void setErrorId(Long errorId) {
        this.errorId = errorId;
    }

    public List<ErrorResponsePayload> getErrorResponsePayloadList() {
        return errorResponsePayloadList;
    }

    public void setErrorResponsePayloadList(List<ErrorResponsePayload> errorResponsePayloadList) {
        this.errorResponsePayloadList = errorResponsePayloadList;
    }
}
