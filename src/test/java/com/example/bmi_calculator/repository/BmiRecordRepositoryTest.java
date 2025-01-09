package com.example.bmi_calculator.repository;

import com.example.bmi_calculator.model.BmiRecord;
import com.example.bmi_calculator.model.User;
import com.example.bmi_calculator.model.User.Gender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BmiRecordRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BmiRecordRepository bmiRecordRepository;

    @Test
    void findByUserOrderByRecordDateDesc_ShouldReturnOrderedRecords() throws InterruptedException {

        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");
        user.setGender(Gender.MALE);
        entityManager.persist(user);


        BmiRecord record1 = new BmiRecord();
        record1.setHeight(170);
        record1.setWeight(70);
        record1.setUser(user);
        entityManager.persist(record1);
        Thread.sleep(100);

        BmiRecord record2 = new BmiRecord();
        record2.setHeight(170);
        record2.setWeight(71);
        record2.setUser(user);
        entityManager.persist(record2);
        Thread.sleep(100);

        BmiRecord record3 = new BmiRecord();
        record3.setHeight(170);
        record3.setWeight(72);
        record3.setUser(user);
        entityManager.persist(record3);

        entityManager.flush();


        List<BmiRecord> records = bmiRecordRepository.findByUserOrderByRecordDateDesc(user);


        assertEquals(3, records.size());
        assertTrue(records.get(0).getRecordDate().isAfter(records.get(1).getRecordDate()));
        assertTrue(records.get(1).getRecordDate().isAfter(records.get(2).getRecordDate()));
    }

    @Test
    void findByUserOrderByRecordDateDesc_ShouldReturnEmptyList_WhenNoRecords() {

        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");
        user.setGender(Gender.MALE);
        entityManager.persist(user);


        List<BmiRecord> records = bmiRecordRepository.findByUserOrderByRecordDateDesc(user);


        assertTrue(records.isEmpty());
    }

    @Test
    void findByUserOrderByRecordDateDesc_ShouldReturnOnlyUserRecords() {

        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("password");
        user1.setGender(Gender.MALE);
        entityManager.persist(user1);

        User user2 = new User();
        user2.setUsername("user2");
        user2.setPassword("password");
        user2.setGender(Gender.FEMALE);
        entityManager.persist(user2);

        BmiRecord record1 = new BmiRecord();
        record1.setHeight(170);
        record1.setWeight(70);
        record1.setUser(user1);
        entityManager.persist(record1);

        BmiRecord record2 = new BmiRecord();
        record2.setHeight(175);
        record2.setWeight(75);
        record2.setUser(user2);
        entityManager.persist(record2);

        entityManager.flush();


        List<BmiRecord> user1Records = bmiRecordRepository.findByUserOrderByRecordDateDesc(user1);
        List<BmiRecord> user2Records = bmiRecordRepository.findByUserOrderByRecordDateDesc(user2);


        assertEquals(1, user1Records.size());
        assertEquals(1, user2Records.size());
        assertEquals(user1, user1Records.get(0).getUser());
        assertEquals(user2, user2Records.get(0).getUser());
    }
}