package com.example.bmi_calculator.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BmiRecordTest {

    private BmiRecord bmiRecord;

    @BeforeEach
    void setUp() {
        bmiRecord = new BmiRecord();
    }

    @Test
    void prePersist_ShouldSetRecordDateAndCalculateBmiAndCategory() {

        bmiRecord.setHeight(180);
        bmiRecord.setWeight(80);


        bmiRecord.prePersist();


        assertNotNull(bmiRecord.getRecordDate());
        assertEquals(24.69, bmiRecord.getBmiValue(), 0.01);
        assertEquals("Normál testsúly", bmiRecord.getCategory());
    }

    @Test
    void preUpdate_ShouldUpdateBmiAndCategory() {

        bmiRecord.setHeight(170);
        bmiRecord.setWeight(90);


        bmiRecord.preUpdate();


        assertEquals(31.14, bmiRecord.getBmiValue(), 0.01);
        assertEquals("Elhízás (I. fokozat)", bmiRecord.getCategory());
    }

    @Test
    void calculateBmi_WithInvalidInput_ShouldReturnZero() {

        bmiRecord.setHeight(0);
        bmiRecord.setWeight(80);


        bmiRecord.prePersist();


        assertEquals(0.0, bmiRecord.getBmiValue());
        assertEquals("Nincs Adat (N/A)", bmiRecord.getCategory());
    }

    @ParameterizedTest
    @CsvSource({
            "170, 50, 17.30, 'Soványság (alultápláltság)'",
            "170, 65, 22.49, 'Normál testsúly'",
            "170, 80, 27.68, 'Túlsúly'",
            "170, 95, 32.87, 'Elhízás (I. fokozat)'",
            "170, 110, 38.06, 'Elhízás (II. fokozat)'",
            "170, 120, 41.52, 'Súlyos elhízás (III. fokozat)'"
    })
    void shouldCalculateCorrectBmiAndCategory(
            double height,
            double weight,
            double expectedBmi,
            String expectedCategory) {

        bmiRecord.setHeight(height);
        bmiRecord.setWeight(weight);


        bmiRecord.prePersist();


        assertEquals(expectedBmi, bmiRecord.getBmiValue(), 0.01);
        assertEquals(expectedCategory, bmiRecord.getCategory());
    }

    @Test
    void shouldSetAndGetUser() {

        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");


        bmiRecord.setUser(user);


        assertEquals(user, bmiRecord.getUser());
    }
}