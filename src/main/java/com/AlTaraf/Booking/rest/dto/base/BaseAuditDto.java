package com.AlTaraf.Booking.rest.dto.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
public abstract class BaseAuditDto<ID extends Serializable> extends BaseDto<Integer> implements Serializable {

    private static final long serialVersionUID = 3960518906571785942L;
    private Integer createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    private Integer lastModifiedBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedDate;
    private Integer deletedFlag;

}
