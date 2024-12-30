package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.database.entity.TechnicalSupportForUnits;
import com.AlTaraf.Booking.database.repository.TechnicalSupportUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TechnicalSupportUnitsService {

    @Autowired
    TechnicalSupportUnitRepository technicalSupportUnitRepository;

    public void saveTechnicalSupport(TechnicalSupportForUnits technicalSupportForUnits) {
        technicalSupportUnitRepository.save(technicalSupportForUnits);
    }

    public Page<TechnicalSupportForUnits> getAllTechnicalSupport(Pageable pageable) {
        return technicalSupportUnitRepository.findAll(pageable);
    }

    public void deleteTechnicalSupportById(Long id) {
        technicalSupportUnitRepository.deleteById(id);
    }

    public void deleteAllTechnicalSupport() {
        technicalSupportUnitRepository.deleteAll();
    }
}
