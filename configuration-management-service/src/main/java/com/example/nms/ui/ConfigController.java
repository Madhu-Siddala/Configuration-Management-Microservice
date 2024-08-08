package com.example.nms.ui;

import com.example.nms.config.ConfigBackup;
import com.example.nms.config.ConfigPush;
import com.example.nms.config.ConfigChangeTracker;
<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Value;
=======
import com.example.nms.config.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> da385fc319984c31edb53b74b74758c03b81d0e2
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class ConfigController {

    private static final Logger logger = LoggerFactory.getLogger(ConfigController.class);

<<<<<<< HEAD
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
=======
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

>>>>>>> da385fc319984c31edb53b74b74758c03b81d0e2
        return "config";  // Return the name of your view template
    }

    @PostMapping("/backup")
    public String backupConfig() {
<<<<<<< HEAD
        ConfigBackup configBackup = new ConfigBackup(routerIp, routerPort, routerUsername, routerPassword);
        configBackup.backupConfig(backupPath);
=======
        ConfigBackup configBackup = new ConfigBackup(configProperties.getRouterIp(), configProperties.getRouterPort(), configProperties.getRouterUsername(), configProperties.getRouterPassword());
        configBackup.backupConfig(configProperties.getBackupPath());
>>>>>>> da385fc319984c31edb53b74b74758c03b81d0e2
        return "redirect:/config";
    }

    @PostMapping("/push")
    public String pushConfig() {
<<<<<<< HEAD
        ConfigPush configPush = new ConfigPush(routerIp, routerPort, routerUsername, routerPassword);
        configPush.pushConfig(configFilePath);

        ConfigChangeTracker configChangeTracker = new ConfigChangeTracker(logFilePath);
        configChangeTracker.logChange("Pushed new configuration from " + configFilePath);

        return "redirect:/config";
    }
}
=======
        ConfigPush configPush = new ConfigPush(configProperties.getRouterIp(), configProperties.getRouterPort(), configProperties.getRouterUsername(), configProperties.getRouterPassword());
        configPush.pushConfig(configProperties.getConfigFilePath());

        ConfigChangeTracker configChangeTracker = new ConfigChangeTracker(configProperties.getLogFilePath());
        configChangeTracker.logChange("Pushed new configuration from " + configProperties.getConfigFilePath());

        return "redirect:/config";
    }
}
>>>>>>> da385fc319984c31edb53b74b74758c03b81d0e2
