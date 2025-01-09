package com.example.bmi_calculator.controller;

import com.example.bmi_calculator.model.User;
import com.example.bmi_calculator.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

class AuthControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_ShouldReturnLoginView() {

        String viewName = authController.login();


        assertEquals("login", viewName);
    }

    @Test
    void registerForm_ShouldAddUserToModelAndReturnRegisterView() {

        String viewName = authController.registerForm(model);


        assertEquals("register", viewName);
        verify(model).addAttribute(eq("user"), any(User.class));
    }

    @Test
    void register_ShouldCallUserServiceAndRedirectToLogin() {

        User user = new User();


        String viewName = authController.register(user);


        verify(userService).registerUser(user);
        assertEquals("redirect:/login", viewName);
    }
}