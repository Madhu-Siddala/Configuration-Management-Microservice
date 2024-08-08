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
        try {
            String backupResult = deviceService.backupConfiguration(ipAddress, username, password);
            model.addAttribute("message", backupResult);
            return "backupSuccess";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Failed to backup configuration: " + e.getMessage());
            return "backupError";
        }
    }

    @PostMapping("/restore")
    public String restoreConfig(@RequestParam String ipAddress, 
                                @RequestParam String username, 
                                @RequestParam String password, 
                                @RequestParam String configData, 
                                Model model) {
        try {
            String result = deviceService.restoreConfiguration(ipAddress, username, password, configData);
            model.addAttribute("message", result);
            return "restoreSuccess";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Failed to restore configuration: " + e.getMessage());
            return "restoreError";
        }
    }

    @PostMapping("/pushConfig")
    public String pushConfig(@RequestParam String ipAddress, 
                             @RequestParam String username, 
                             @RequestParam String password, 
                             @RequestParam String configCommands, 
                             Model model) {
        try {
            String result = deviceService.pushConfiguration(ipAddress, username, password, configCommands);
            model.addAttribute("message", result);
            return "pushConfigSuccess";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Failed to push configuration: " + e.getMessage());
            return "pushConfigError";
        }
    }

    @PostMapping("/trackChanges")
    public String trackConfigChanges(@RequestParam String ipAddress, 
                                     @RequestParam String username, 
                                     @RequestParam String password, 
                                     Model model) {
        try {
            String differences = deviceService.trackConfigurationChanges(ipAddress, username, password);
            model.addAttribute("message", differences);
            return "trackChangesSuccess";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Failed to track configuration changes: " + e.getMessage());
            return "trackChangesError";
        }
    }
}
