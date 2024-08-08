package com.example.nms.config;

import java.io.*;
import java.net.*;
import java.util.Properties;

public class ConfigPush {
    private String routerIp; // Router IP address
    private int routerPort; // Router port (e.g., 23 for Telnet)
    private String username; // Username for the router
    private String password; // Password for the router
    private String loginCommand; // Command to send the username
    private String enableCommand; // Command to enter enable mode
    private String terminalCommand; // Command to set terminal length
    private String configureCommand; // Command to enter configuration mode
    private String configFilePath; // Path to the configuration file to push

    // Constructor to initialize the properties from config file
    public ConfigPush() {
        loadConfig();
    }

    // Method to load properties from config.properties
    private void loadConfig() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }
            prop.load(input);
            // Load properties
            this.routerIp = prop.getProperty("router.ip");
            this.routerPort = Integer.parseInt(prop.getProperty("router.port"));
            this.username = prop.getProperty("router.username");
            this.password = prop.getProperty("router.password");
            this.loginCommand = prop.getProperty("commands.login");
            this.enableCommand = prop.getProperty("commands.enable");
            this.terminalCommand = prop.getProperty("commands.terminal");
            this.configureCommand = prop.getProperty("commands.configure");
            this.configFilePath = prop.getProperty("config.file.path");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Method to push the configuration to the router
    public void pushConfig() {
        try (Socket socket = new Socket(routerIp, routerPort);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            // Send login command
            writer.println(username);
            writer.flush();
            Thread.sleep(1000); // Wait for the router to prompt for password

            // Send password
            writer.println(password);
            writer.flush();
            Thread.sleep(1000); // Wait for password acceptance

            // Navigate to enable mode
            writer.println(enableCommand);
            writer.flush();
            Thread.sleep(1000); // Wait for enable mode

            // Set terminal length
            writer.println(terminalCommand);
            writer.flush();
            Thread.sleep(1000); // Wait for command execution

            // Enter configuration mode
            writer.println(configureCommand);
            writer.flush();
            Thread.sleep(1000); // Wait for config mode

            // Push the configuration from the file
            try (BufferedReader fileReader = new BufferedReader(new FileReader(configFilePath))) {
                String line;
                while ((line = fileReader.readLine()) != null) {
                    writer.println(line);
                    writer.flush();
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
