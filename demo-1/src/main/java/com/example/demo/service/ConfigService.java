package com.example.demo.service;


import com.example.demo.util.SSHExecutor;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Service
public class ConfigService {

    private static final String COMMANDS_DIR = "src/main/resources/commands/";
    private static final String OUTPUT_DIR = "output/";

    public List<String> getAvailableDevices() {
        // Placeholder for actual device retrieval logic
        return Arrays.asList("Device1", "Device2", "Device3");
    }

    public List<String> getAvailableConfigs() {
        try {
            return Files.list(Paths.get(COMMANDS_DIR))
                        .map(Path::getFileName)
                        .map(Path::toString)
                        .toList();
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public String executeConfig(String device, String configFile) {
        // Placeholder for SSH logic using SSHExecutor
        SSHExecutor executor = new SSHExecutor("host", "username", "password");
        return executor.executeCommandsFromXml(COMMANDS_DIR + configFile);
    }

    public void saveOutputToFile(String output, String fileName) {
        Path outputPath = Paths.get(OUTPUT_DIR + fileName);
        try (FileWriter writer = new FileWriter(outputPath.toFile())) {
            writer.write(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
