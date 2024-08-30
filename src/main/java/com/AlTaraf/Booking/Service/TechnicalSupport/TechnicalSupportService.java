package com.AlTaraf.Booking.Service.TechnicalSupport;

import com.AlTaraf.Booking.Entity.TechnicalSupport.TechnicalSupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TechnicalSupportService {

    void saveTechnicalSupport(TechnicalSupport technicalSupport);

    Page<TechnicalSupport> getAllTechnicalSupport(Pageable pageable);

    void deleteTechnicalSupportById(Long id);
    void deleteAllTechnicalSupport();

    Page<TechnicalSupport> getTechnicalSupportBySeen(boolean seen, Pageable pageable);

}
