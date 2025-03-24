package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.database.entity.ErrorLog;
import com.AlTaraf.Booking.database.repository.ErrorLogRepository;
import com.AlTaraf.Booking.service.base.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ErrorLogService extends BaseService<Integer, ErrorLog> {

    private ErrorLogRepository errorLogRepository;

    @Override
    public ErrorLogRepository getRepository() {
        return errorLogRepository;
    }
}
