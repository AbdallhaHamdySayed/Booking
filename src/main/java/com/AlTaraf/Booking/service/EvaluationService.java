package com.AlTaraf.Booking.service;


import com.AlTaraf.Booking.database.entity.Evaluation;
import com.AlTaraf.Booking.database.repository.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluationService {
    @Autowired
    EvaluationRepository evaluationRepository;

    public List<Evaluation> getAllEvaluation() {
        return evaluationRepository.findAll();
    }

}
