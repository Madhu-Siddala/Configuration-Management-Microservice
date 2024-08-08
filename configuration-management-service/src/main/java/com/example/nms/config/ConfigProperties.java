package com.example.nms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigProperties {
    
    @Value("${router.username}")
    private String routerUsername;

    @Value("${router.password}")
    private String routerPassword;

    @Value("${router.ip}")
    private String routerIp;

    @Value("${router.port}")
    private int routerPort;

    @Value("${commands.login}")
    private String loginCommand;

    @Value("${commands.enable}")
    private String enableCommand;

    @Value("${commands.terminal}")
    private String terminalCommand;

    @Value("${commands.configure}")
    private String configureCommand;

    @Value("${log.file.path}")
    private String logFilePath;

    @Value("${backup.path}")
    private String backupPath;

    @Value("${config.file.path}")
    private String configFilePath;

    // Getters for all properties
    public String getRouterUsername() {
        return routerUsername;
    }

    public String getRouterPassword() {
        return routerPassword;
    }

    public String getRouterIp() {
        return routerIp;
    }

    public int getRouterPort() {
        return routerPort;
    }

    public String getLoginCommand() {
        return loginCommand;
    }

    public String getEnableCommand() {
        return enableCommand;
    }

    public String getTerminalCommand() {
        return terminalCommand;
    }

    public String getConfigureCommand() {
        return configureCommand;
    }

    public String getLogFilePath() {
        return logFilePath;
    }

    public String getBackupPath() {
        return backupPath;
    }

    public String getConfigFilePath() {
        return configFilePath;
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> da385fc319984c31edb53b74b74758c03b81d0e2
