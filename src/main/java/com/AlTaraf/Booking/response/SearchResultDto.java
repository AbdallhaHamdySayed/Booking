package com.AlTaraf.Booking.response;


import com.zad.altamim.service.rest.dto.base.BaseDto;

import java.io.Serializable;
import java.util.List;

public class SearchResultDto<ID extends Serializable, T extends BaseDto<ID>> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5325587568782411009L;
    private List<T> resultData;
    private long totalItemsCount;

    public SearchResultDto(List<T> resultData, long totalElements) {
        super();
        this.resultData = resultData;
        this.totalItemsCount = totalElements;
    }

    public List<T> getResultData() {
        return resultData;
    }

    public void setResultData(List<T> resultData) {
        this.resultData = resultData;
    }

    public long getTotalItemsCount() {
        return totalItemsCount;
    }

    public void setTotalItemsCount(long totalItemsCount) {
        this.totalItemsCount = totalItemsCount;
    }
}
