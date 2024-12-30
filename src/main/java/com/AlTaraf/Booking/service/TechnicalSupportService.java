package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.database.entity.TechnicalSupport;
import com.AlTaraf.Booking.database.repository.TechnicalSupportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TechnicalSupportService {

    @Autowired
    TechnicalSupportRepository technicalSupportRepository;

    public void saveTechnicalSupport(TechnicalSupport technicalSupport) {
        technicalSupportRepository.save(technicalSupport);
    }

    public Page<TechnicalSupport> getAllTechnicalSupport(Pageable pageable) {
        return technicalSupportRepository.findAll(pageable);
    }

    public void deleteTechnicalSupportById(Long id) {
        technicalSupportRepository.deleteById(id);
    }

    public void deleteAllTechnicalSupport() {
        technicalSupportRepository.deleteAll();
    }

    public Page<TechnicalSupport> getTechnicalSupportBySeen(boolean seen, Pageable pageable) {
        return technicalSupportRepository.findBySeen(seen, pageable);
    }

}
