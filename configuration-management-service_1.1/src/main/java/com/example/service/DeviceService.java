package com.example.service;

import com.example.network.config.DeviceConfig;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

    public String backupConfiguration(String ipAddress, String username, String password) throws Exception {
        DeviceConfig deviceConfig = new DeviceConfig(ipAddress, username, password);
        return deviceConfig.backupConfig();
    }

    public String restoreConfiguration(String ipAddress, String username, String password, String configData) throws Exception {
        DeviceConfig deviceConfig = new DeviceConfig(ipAddress, username, password);
        return deviceConfig.restoreConfig(configData);
    }

    public String pushConfiguration(String ipAddress, String username, String password, String configCommands) throws Exception {
        DeviceConfig deviceConfig = new DeviceConfig(ipAddress, username, password);
        return deviceConfig.pushConfig(configCommands);
    }

    public String trackConfigurationChanges(String ipAddress, String username, String password) throws Exception {
        DeviceConfig deviceConfig = new DeviceConfig(ipAddress, username, password);
        return deviceConfig.trackConfigChanges();
    }

    public String executeCustomCommand(String ipAddress, String username, String password, String command) throws Exception {
        DeviceConfig deviceConfig = new DeviceConfig(ipAddress, username, password);
        return deviceConfig.executeCustomCommand(command);
    }
}
