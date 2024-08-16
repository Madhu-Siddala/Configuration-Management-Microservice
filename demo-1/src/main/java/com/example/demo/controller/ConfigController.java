package com.example.demo.controller;


import com.example.demo.service.ConfigService;
import com.example.demo.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @Autowired
    private LogService logService;

    @GetMapping("/")
    public String index(Model model) {
        List<String> devices = configService.getAvailableDevices();
        List<String> configs = configService.getAvailableConfigs();

        model.addAttribute("devices", devices);
        model.addAttribute("configs", configs);

        return "index";
    }

    @PostMapping("/execute")
    public String executeCommand(@RequestParam("device") String device,
                                 @RequestParam("configFile") String configFile,
                                 Model model) {
        String result = configService.executeConfig(device, configFile);
        logService.logConfigPush(device, result);

        model.addAttribute("result", result);
        return "result";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<String> logs = logService.getLogs();
        model.addAttribute("logs", logs);

        return "dashboard";
    }

    @PostMapping("/saveOutput")
    public String saveOutput(@RequestParam("output") String output,
                             @RequestParam("fileName") String fileName,
                             Model model) {
        configService.saveOutputToFile(output, fileName);
        model.addAttribute("message", "Output saved to " + fileName);

        return "result";
    }
}

