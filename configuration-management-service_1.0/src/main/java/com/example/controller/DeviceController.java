package com.example.controller;

import com.example.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/backup")
    public String backupConfig(@RequestParam String ipAddress, 
                               @RequestParam String username, 
                               @RequestParam String password, 
                               Model model) {
        String backupResult = deviceService.backupConfiguration(ipAddress, username, password);
        model.addAttribute("message", backupResult);
        return "result";
    }

    @PostMapping("/push")
    public String pushConfig(@RequestParam String ipAddress, 
                             @RequestParam String username, 
                             @RequestParam String password, 
                             @RequestParam String configuration, 
                             Model model) {
        String pushResult = deviceService.pushConfiguration(ipAddress, username, password, configuration);
        model.addAttribute("message", pushResult);
        return "result";
    }
}
