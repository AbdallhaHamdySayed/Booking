package com.AlTaraf.Booking.Service.TechnicalSupport;

import com.AlTaraf.Booking.Entity.TechnicalSupport.TechnicalSupport;
import com.AlTaraf.Booking.Entity.TechnicalSupport.TechnicalSupportForUnits;
import com.AlTaraf.Booking.Repository.technicalSupport.TechnicalSupportRepository;
import com.AlTaraf.Booking.Repository.technicalSupport.TechnicalSupportUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TechnicalSupportUnitServiceImpl implements TechnicalSupportUnitsService {

    @Autowired
    TechnicalSupportUnitRepository technicalSupportUnitRepository;

    @Override
    public void saveTechnicalSupport(TechnicalSupportForUnits technicalSupportForUnits) {
        technicalSupportUnitRepository.save(technicalSupportForUnits);
    }

    @Override
    public Page<TechnicalSupportForUnits> getAllTechnicalSupport(Pageable pageable) {
        return technicalSupportUnitRepository.findAll(pageable);
    }

    @Override
    public void deleteTechnicalSupportById(Long id) {
        technicalSupportUnitRepository.deleteById(id);
    }

    @Override
    public void deleteAllTechnicalSupport() {
        technicalSupportUnitRepository.deleteAll();
    }


}
