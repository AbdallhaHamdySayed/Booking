package com.AlTaraf.Booking.Service.TechnicalSupport;

import com.AlTaraf.Booking.Entity.TechnicalSupport.TechnicalSupport;
import com.AlTaraf.Booking.Entity.TechnicalSupport.TechnicalSupportForUnits;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TechnicalSupportUnitsService {

    void saveTechnicalSupport(TechnicalSupportForUnits technicalSupport);

    Page<TechnicalSupportForUnits> getAllTechnicalSupport(Pageable pageable);

    void deleteTechnicalSupportById(Long id);
    void deleteAllTechnicalSupport();
}
