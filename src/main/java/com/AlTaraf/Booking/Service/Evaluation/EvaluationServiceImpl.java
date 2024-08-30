package com.AlTaraf.Booking.Service.Evaluation;

import com.AlTaraf.Booking.Entity.Evaluation.Evaluation;
import com.AlTaraf.Booking.Repository.Evaluation.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluationServiceImpl implements EvaluationService {

    @Autowired
    EvaluationRepository evaluationRepository;

    @Override
    public List<Evaluation> getAllEvaluation() {
        return evaluationRepository.findAll();
    }
}
