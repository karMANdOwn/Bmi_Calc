package com.example.bmi_calculator.controller;

import com.example.bmi_calculator.model.BmiRecord;
import com.example.bmi_calculator.model.User;
import com.example.bmi_calculator.service.BmiService;
import com.example.bmi_calculator.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class BmiController {

    private final BmiService bmiService;
    private final UserRepository userRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("bmiRecord", new BmiRecord());
        model.addAttribute("bmiHistory", bmiService.getUserBmiHistory(user));
        return "dashboard";
    }

    @PostMapping("/calculate-bmi")
    public String calculateBmi(@ModelAttribute BmiRecord bmiRecord, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName());
        bmiRecord.setUser(user);
        bmiService.saveBmiRecord(bmiRecord);
        return "redirect:/dashboard";
    }
}