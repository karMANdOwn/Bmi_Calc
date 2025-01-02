package com.example.bmi_calculator.service;

import com.example.bmi_calculator.model.BmiRecord;
import com.example.bmi_calculator.model.User;
import com.example.bmi_calculator.repository.BmiRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BmiService {

    private final BmiRecordRepository bmiRecordRepository;

    public BmiRecord saveBmiRecord(BmiRecord bmiRecord) {
        return bmiRecordRepository.save(bmiRecord);
    }

    public List<BmiRecord> getUserBmiHistory(User user) {
        return bmiRecordRepository.findByUserOrderByRecordDateDesc(user);
    }
}