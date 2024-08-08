package com.example.nms.ui;

import com.example.nms.config.ConfigBackup;
import com.example.nms.config.ConfigPush;
import com.example.nms.config.ConfigChangeTracker;
import com.example.nms.config.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class ConfigController {

    private static final Logger logger = LoggerFactory.getLogger(ConfigController.class);

    @Autowired
    private ConfigProperties configProperties;

    @GetMapping("/config")
    public String configPage(Model model) {
        // Log the properties
        logger.info("Router IP: {}", configProperties.getRouterIp());
        logger.info("Router Port: {}", configProperties.getRouterPort());
        logger.info("Router Username: {}", configProperties.getRouterUsername());
        logger.info("Router Password: {}", configProperties.getRouterPassword());
        logger.info("Backup Path: {}", configProperties.getBackupPath());
        logger.info("Config File Path: {}", configProperties.getConfigFilePath());
        logger.info("Log File Path: {}", configProperties.getLogFilePath());

        return "config";  // Return the name of your view template
    }

    @PostMapping("/backup")
    public String backupConfig() {
        ConfigBackup configBackup = new ConfigBackup(configProperties.getRouterIp(), configProperties.getRouterPort(), configProperties.getRouterUsername(), configProperties.getRouterPassword());
        configBackup.backupConfig(configProperties.getBackupPath());
        return "redirect:/config";
    }

    @PostMapping("/push")
    public String pushConfig() {
        ConfigPush configPush = new ConfigPush(configProperties.getRouterIp(), configProperties.getRouterPort(), configProperties.getRouterUsername(), configProperties.getRouterPassword());
        configPush.pushConfig(configProperties.getConfigFilePath());

        ConfigChangeTracker configChangeTracker = new ConfigChangeTracker(configProperties.getLogFilePath());
        configChangeTracker.logChange("Pushed new configuration from " + configProperties.getConfigFilePath());

        return "redirect:/config";
    }
}