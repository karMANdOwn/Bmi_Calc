package com.example.bmi_calculator.controller;

import com.example.bmi_calculator.model.BmiRecord;
import com.example.bmi_calculator.model.User;
import com.example.bmi_calculator.service.BmiService;
import com.example.bmi_calculator.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BmiControllerTest {

    @Mock
    private BmiService bmiService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Model model;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private BmiController bmiController;

    private User testUser;
    private List<BmiRecord> bmiHistory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

       //tesztusder
        testUser = new User();
        testUser.setUsername("testUser");

        //BMI history lita
        bmiHistory = new ArrayList<>();

        //autantikácio
        when(authentication.getName()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(testUser);
        when(bmiService.getUserBmiHistory(testUser)).thenReturn(bmiHistory);
    }

    @Test
    void dashboard_ShouldAddAttributesToModelAndReturnDashboardView() {

        String viewName = bmiController.dashboard(model, authentication);


        verify(model).addAttribute("username", "testUser");
        verify(model).addAttribute("bmiRecord", new BmiRecord());
        verify(model).addAttribute("bmiHistory", bmiHistory);
        assertEquals("dashboard", viewName);
    }

    @Test
    void calculateBmi_ShouldSaveBmiRecordAndRedirectToDashboard() {

        BmiRecord bmiRecord = new BmiRecord();


        String viewName = bmiController.calculateBmi(bmiRecord, authentication);


        verify(bmiService).saveBmiRecord(bmiRecord);
        assertEquals(testUser, bmiRecord.getUser());
        assertEquals("redirect:/dashboard", viewName);
    }
}