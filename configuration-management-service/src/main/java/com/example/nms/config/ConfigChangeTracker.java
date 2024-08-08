package com.example.nms.config;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.Properties;

public class ConfigChangeTracker {
    private String logFilePath; // Path to the log file

    // Constructor to initialize the properties from config file
    public ConfigChangeTracker() {
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
            this.logFilePath = prop.getProperty("log.file.path");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Method to log configuration changes
    public void logChange(String configChange) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(logFilePath), 
                StandardOpenOption.APPEND, StandardOpenOption.CREATE)) {
            writer.write(LocalDateTime.now() + " - " + configChange);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
