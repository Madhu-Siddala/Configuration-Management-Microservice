package com.example.nms.config;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigChangeTracker {
    private String logFilePath;

    public ConfigChangeTracker(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    public void logChange(String changeDetails) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))) {
            writer.write(changeDetails);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
