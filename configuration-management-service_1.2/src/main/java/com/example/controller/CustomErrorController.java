package com.example.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @GetMapping("/error")
    public String handleError() {
        // Return a custom error view
        return "error";
    }

   
    public String getErrorPath() {
        return "/error";
    }
}