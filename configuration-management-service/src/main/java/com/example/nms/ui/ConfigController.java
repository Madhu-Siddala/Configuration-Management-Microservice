package com.example.nms.ui;

import com.example.nms.config.ConfigBackup;
import com.example.nms.config.ConfigPush;
import com.example.nms.config.ConfigChangeTracker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class ConfigController {

    private static final Logger logger = LoggerFactory.getLogger(ConfigController.class);

    @Value("${router.ip}")
    private String routerIp;

    @Value("${router.port}")
    private int routerPort;

    @Value("${router.username}")
    private String routerUsername;

    @Value("${router.password}")
    private String routerPassword;

    @Value("${backup.path}")
    private String backupPath;

    @Value("${config.file.path}")
    private String configFilePath;

    @Value("${log.file.path}")
    private String logFilePath;

    @GetMapping("/config")
    public String configPage(Model model) {
        logger.info("Router IP: {}", routerIp);
        logger.info("Router Port: {}", routerPort);
        logger.info("Router Username: {}", routerUsername);
        logger.info("Router Password: {}", routerPassword);
        logger.info("Backup Path: {}", backupPath);
        logger.info("Config File Path: {}", configFilePath);
        logger.info("Log File Path: {}", logFilePath);
        return "config";  // Return the name of your view template
    }

    @PostMapping("/backup")
    public String backupConfig() {
        ConfigBackup configBackup = new ConfigBackup(routerIp, routerPort, routerUsername, routerPassword);
        configBackup.backupConfig(backupPath);
        return "redirect:/config";
    }

    @PostMapping("/push")
    public String pushConfig() {
        ConfigPush configPush = new ConfigPush(routerIp, routerPort, routerUsername, routerPassword);
        configPush.pushConfig(configFilePath);

        ConfigChangeTracker configChangeTracker = new ConfigChangeTracker(logFilePath);
        configChangeTracker.logChange("Pushed new configuration from " + configFilePath);

        return "redirect:/config";
    }
}
