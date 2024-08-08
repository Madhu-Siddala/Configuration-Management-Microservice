package com.example.service;

import com.example.util.SSHUtil;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

    public String backupConfiguration(String ipAddress, String username, String password) {
        // Command to backup the running configuration
        String command = "show running-config";
        return SSHUtil.executeCommand(ipAddress, username, password, command);
    }

    public String pushConfiguration(String ipAddress, String username, String password, String configuration) {
        // Command to enter global configuration mode and apply the provided configuration
        String command = "configure terminal\n" + configuration + "\nend";
        return SSHUtil.executeCommand(ipAddress, username, password, command);
    }
}
