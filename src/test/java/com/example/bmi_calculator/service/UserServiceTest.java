package com.example.bmi_calculator.service;

import com.example.bmi_calculator.model.User;
import com.example.bmi_calculator.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUsername("testUser");
        testUser.setPassword("password123");
    }

    @Test
    void registerUser_ShouldEncodePasswordAndSaveUser() {

        String encodedPassword = "encodedPassword123";
        when(passwordEncoder.encode(testUser.getPassword())).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(testUser);


        User savedUser = userService.registerUser(testUser);


        assertEquals(encodedPassword, savedUser.getPassword());
        assertEquals(testUser.getUsername(), savedUser.getUsername());
    }

    @Test
    void loadUserByUsername_ShouldReturnUserDetails_WhenUserExists() {

        when(userRepository.findByUsername(testUser.getUsername())).thenReturn(testUser);


        UserDetails userDetails = userService.loadUserByUsername(testUser.getUsername());


        assertNotNull(userDetails);
        assertEquals(testUser.getUsername(), userDetails.getUsername());
        assertEquals(testUser.getPassword(), userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
    }

    @Test
    void loadUserByUsername_ShouldThrowException_WhenUserNotFound() {

        String nonExistentUsername = "nonexistent";
        when(userRepository.findByUsername(nonExistentUsername)).thenReturn(null);


        assertThrows(UsernameNotFoundException.class, () ->
                userService.loadUserByUsername(nonExistentUsername)
        );
    }
}