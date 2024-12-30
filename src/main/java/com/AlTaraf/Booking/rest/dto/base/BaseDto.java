package com.AlTaraf.Booking.rest.dto.base;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class BaseDto<ID extends Serializable> implements Serializable {

    private static final long serialVersionUID = 1088392855495235383L;
    private ID id;
}
