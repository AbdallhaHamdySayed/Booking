package com.AlTaraf.Booking.response;


import com.zad.altamim.service.response.base.BaseResponse;
import com.zad.altamim.service.response.keys.ErrorResponsePayload;
import com.zad.altamim.service.response.keys.ErrorStatus;
import com.zad.altamim.service.support.utils.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ErrorResponse extends BaseResponse implements Serializable {

    private List<ErrorResponsePayload> errorResponsePayloadList = new ArrayList<>();
    @DateTimeFormat(style = "DD-MM-YYYY HH:MM:SS")
    private LocalDateTime timeStamp;
    private Integer errorId;

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

    public ErrorResponse(String errorKey, Integer errorId) {
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

    public Integer getErrorId() {
        return errorId;
    }

    public void setErrorId(Integer errorId) {
        this.errorId = errorId;
    }

    public List<ErrorResponsePayload> getErrorResponsePayloadList() {
        return errorResponsePayloadList;
    }

    public void setErrorResponsePayloadList(List<ErrorResponsePayload> errorResponsePayloadList) {
        this.errorResponsePayloadList = errorResponsePayloadList;
    }
}
