package com.example.demo.service;


import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogService {

    private static final String LOG_DIR = "logs/";

    public LogService() {
        createLogDirectory();
    }

    private void createLogDirectory() {
        Path path = Paths.get(LOG_DIR);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void logConfigPush(String device, String commandOutput) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String logFileName = LOG_DIR + "config_push_" + device + "_" + timestamp + ".log";

        try (FileWriter writer = new FileWriter(logFileName)) {
            writer.write("Timestamp: " + timestamp + "\n");
            writer.write("Device: " + device + "\n");
            writer.write("Output:\n" + commandOutput + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logBackup(String device, String backupData) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String logFileName = LOG_DIR + "backup_" + device + "_" + timestamp + ".log";

        try (FileWriter writer = new FileWriter(logFileName)) {
            writer.write("Timestamp: " + timestamp + "\n");
            writer.write("Device: " + device + "\n");
            writer.write("Backup Data:\n" + backupData + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getLogs() {
        try {
            return Files.list(Paths.get(LOG_DIR))
                        .map(Path::getFileName)
                        .map(Path::toString)
                        .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }
}

