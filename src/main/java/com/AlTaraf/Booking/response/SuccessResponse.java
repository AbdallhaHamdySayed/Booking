package com.AlTaraf.Booking.response;


import com.AlTaraf.Booking.response.base.BaseResponse;

import java.io.Serializable;

public class SuccessResponse<T extends Serializable> extends BaseResponse {
    private T resultData;

    public SuccessResponse(T resultData) {
        super();
        this.resultData = resultData;
    }

    public SuccessResponse() {
        super();
    }

    public T getResultData() {
        return resultData;
    }

    public void setResultData(T resultData) {
        this.resultData = resultData;
    }
}
