package com.example.nms.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ConfigPush {
    private String routerIp;
    private int routerPort;
    private String routerUsername;
    private String routerPassword;

    public ConfigPush(String routerIp, int routerPort, String routerUsername, String routerPassword) {
        this.routerIp = routerIp;
        this.routerPort = routerPort;
        this.routerUsername = routerUsername;
        this.routerPassword = routerPassword;
    }

    public void pushConfig(String configFilePath) {
        try (Socket socket = new Socket(routerIp, routerPort);
             PrintWriter writer = new PrintWriter(socket.getOutputStream());
             BufferedReader reader = new BufferedReader(new FileReader(configFilePath))) {

            // Login and push the configuration
            writer.println(routerUsername);
            writer.flush();
            // Simulating password entry with wait time (adjust as necessary)
            Thread.sleep(1000);
            writer.println(routerPassword);
            writer.flush();
            Thread.sleep(1000);

            String line;
            while ((line = reader.readLine()) != null) {
                writer.println(line);
                writer.flush();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
