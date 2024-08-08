package com.example.nms.config;

<<<<<<< HEAD
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ConfigBackup {
    private String routerIp;
    private int routerPort;
    private String routerUsername;
    private String routerPassword;

    public ConfigBackup(String routerIp, int routerPort, String routerUsername, String routerPassword) {
        this.routerIp = routerIp;
        this.routerPort = routerPort;
        this.routerUsername = routerUsername;
        this.routerPassword = routerPassword;
    }

    public void backupConfig(String backupPath) {
        try (Socket socket = new Socket(routerIp, routerPort);
             PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(backupPath)))) {
            // Implement backup logic using socket connection
            writer.println("show running-config"); // Example command
            writer.flush();
        } catch (IOException e) {
=======
import java.io.*;
import java.net.*;
import java.util.Properties;

public class ConfigBackup {
    private String routerIp; // Router IP address
    private int routerPort; // Router port (e.g., 23 for Telnet)
    private String username; // Username for the router
    private String password; // Password for the router
    private String loginCommand; // Command to send the username
    private String enableCommand; // Command to enter enable mode
    private String terminalCommand; // Command to set terminal length
    private String configureCommand; // Command to enter configuration mode
    private String backupPath; // Path to save the backup configuration

    // Constructor to initialize the properties from config file
    public ConfigBackup() {
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
            this.backupPath = prop.getProperty("backup.path");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Method to backup the router's configuration
    public void backupConfig() {
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

            // Save the configuration to the backup file
            try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(backupPath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    fileWriter.write(line);
                    fileWriter.newLine();
                }
            }
        } catch (IOException | InterruptedException e) {
>>>>>>> da385fc319984c31edb53b74b74758c03b81d0e2
            e.printStackTrace();
        }
    }
}
