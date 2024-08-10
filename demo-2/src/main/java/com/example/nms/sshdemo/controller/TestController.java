package com.example.nms.sshdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TestController {

    @PostMapping("/test")
    public String test(Model model) {
        model.addAttribute("message", "Test successful!");
        return "result";
    }
}
