package com.example.bmi_calculator.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "bmi_records")
public class BmiRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double height;

    @Column(nullable = false)
    private double weight;

    @Column(nullable = false)
    private double bmiValue;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private LocalDateTime recordDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PrePersist
    public void prePersist() {
        recordDate = LocalDateTime.now();
        bmiValue = calculateBmi();
        category = calculateBmiCategory(bmiValue);
    }

    @PreUpdate
    public void preUpdate() {
        bmiValue = calculateBmi();
        category = calculateBmiCategory(bmiValue);
    }

    private double calculateBmi() {
        if (height <= 0 || weight <= 0) {
            return 0.0;
        }
        double heightInMeters = height / 100.0;
        return weight / (heightInMeters * heightInMeters);
    }

    private String calculateBmiCategory(double bmiValue) {
        if (bmiValue == 0.0) {
            return "Nincs Adat (N/A)";
        } else if (bmiValue < 18.5) {
            return "Soványság (alultápláltság)";
        } else if (bmiValue < 25.0) {
            return "Normál testsúly";
        } else if (bmiValue < 30.0) {
            return "Túlsúly";
        } else if (bmiValue < 35.0) {
            return "Elhízás (I. fokozat)";
        } else if (bmiValue < 40.0) {
            return "Elhízás (II. fokozat)";
        } else {
            return "Súlyos elhízás (III. fokozat)";
        }
    }
}