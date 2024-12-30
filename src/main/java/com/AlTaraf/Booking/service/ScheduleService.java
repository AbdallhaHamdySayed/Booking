package com.AlTaraf.Booking.service;


import com.AlTaraf.Booking.database.entity.Schedule;
import com.AlTaraf.Booking.database.repository.ScheduleRepository;
import com.AlTaraf.Booking.service.base.BaseService;
import com.AlTaraf.Booking.support.utils.DateUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class ScheduleService extends BaseService<Integer, Schedule> {

    private final ScheduleRepository scheduleRepository;

    @Override
    public ScheduleRepository getRepository() {
        return scheduleRepository;
    }

    public Schedule createEntity(String operation) {
        Schedule schedule =new Schedule();
        schedule.setStartDate(DateUtils.getCurrentDate());
        schedule.setUuid(UUID.randomUUID().toString());
        schedule.setOperation(operation);
        return super.createEntity(schedule);
    }

    public Schedule updateEntity(Schedule entity) {
        entity.setEndDate(DateUtils.getCurrentDate());
        return super.updateEntity(entity);
    }

}
