package com.example.bmi_calculator.repository;

import com.example.bmi_calculator.model.BmiRecord;
import com.example.bmi_calculator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BmiRecordRepository extends JpaRepository<BmiRecord, Long> {
    List<BmiRecord> findByUserOrderByRecordDateDesc(User user);
}
