package com.AlTaraf.Booking.Service.unit.availableArea;

import com.AlTaraf.Booking.Entity.unit.availableArea.AvailableArea;

import java.util.List;

public interface AvailableAreaService {
    List<AvailableArea> getAllAvailableArea();
    AvailableArea save(AvailableArea availableArea);

    AvailableArea getById(Long availableAreaId);
}
