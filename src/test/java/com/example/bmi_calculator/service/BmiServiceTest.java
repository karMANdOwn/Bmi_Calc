package com.example.bmi_calculator.service;

import com.example.bmi_calculator.model.BmiRecord;
import com.example.bmi_calculator.model.User;
import com.example.bmi_calculator.repository.BmiRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BmiServiceTest {

    @Mock
    private BmiRecordRepository bmiRecordRepository;

    @InjectMocks
    private BmiService bmiService;

    @Test
    void saveBmiRecord_ShouldReturnSavedRecord() {
        // Given
        BmiRecord record = new BmiRecord();
        record.setHeight(180);
        record.setWeight(80);
        when(bmiRecordRepository.save(record)).thenReturn(record);

        // When
        BmiRecord savedRecord = bmiService.saveBmiRecord(record);

        // Then
        assertThat(savedRecord).isNotNull();
        assertThat(savedRecord.getHeight()).isEqualTo(180);
        assertThat(savedRecord.getWeight()).isEqualTo(80);
    }

    @Test
    void getUserBmiHistory_ShouldReturnUserRecords() {

        User user = new User();
        BmiRecord record1 = new BmiRecord();
        BmiRecord record2 = new BmiRecord();
        List<BmiRecord> records = Arrays.asList(record1, record2);
        
        when(bmiRecordRepository.findByUserOrderByRecordDateDesc(user)).thenReturn(records);


        List<BmiRecord> result = bmiService.getUserBmiHistory(user);


        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(record1, record2);
    }
}
