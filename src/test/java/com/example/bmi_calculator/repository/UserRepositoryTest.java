package com.example.bmi_calculator.repository;

import com.example.bmi_calculator.model.User;
import com.example.bmi_calculator.model.User.Gender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUsername_ShouldReturnUser_WhenUserExists() {

        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");
        user.setGender(Gender.MALE);
        entityManager.persist(user);
        entityManager.flush();


        User found = userRepository.findByUsername("testUser");


        assertNotNull(found);
        assertEquals("testUser", found.getUsername());
        assertEquals("password", found.getPassword());
        assertEquals(Gender.MALE, found.getGender());
    }

    @Test
    void findByUsername_ShouldReturnNull_WhenUserDoesNotExist() {

        User found = userRepository.findByUsername("nonExistentUser");


        assertNull(found);
    }

    @Test
    void findByUsername_ShouldReturnCorrectUser_WhenMultipleUsersExist() {

        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("password1");
        user1.setGender(Gender.MALE);
        entityManager.persist(user1);

        User user2 = new User();
        user2.setUsername("user2");
        user2.setPassword("password2");
        user2.setGender(Gender.FEMALE);
        entityManager.persist(user2);

        entityManager.flush();


        User foundUser1 = userRepository.findByUsername("user1");
        User foundUser2 = userRepository.findByUsername("user2");


        assertNotNull(foundUser1);
        assertNotNull(foundUser2);
        assertEquals("user1", foundUser1.getUsername());
        assertEquals("user2", foundUser2.getUsername());
        assertEquals("password1", foundUser1.getPassword());
        assertEquals("password2", foundUser2.getPassword());
        assertEquals(Gender.MALE, foundUser1.getGender());
        assertEquals(Gender.FEMALE, foundUser2.getGender());
    }
}