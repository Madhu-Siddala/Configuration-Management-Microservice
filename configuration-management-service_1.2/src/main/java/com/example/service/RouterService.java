package com.example.service;

import com.jcraft.jsch.*;

import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.io.PrintStream;

@Service
public class RouterService {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "wipro";
    private static final String HOST = "172.20.0.2";
    private static final int PORT = 22;

    public String changeHostname(String newHostname) {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(USERNAME, HOST, PORT);
            session.setPassword(PASSWORD);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            Channel channel = session.openChannel("shell");
            channel.setInputStream(System.in);
            channel.setOutputStream(System.out);

            OutputStream out = channel.getOutputStream();
            PrintStream ps = new PrintStream(out, true);
            channel.connect();

            ps.println("configure terminal");
            ps.println("hostname " + newHostname);
            ps.println("end");
            ps.println("write memory");
            ps.println("exit");

            Thread.sleep(5000);

            channel.disconnect();
            session.disconnect();
            return "Hostname changed to " + newHostname;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    // Implement backup, restore, and push configuration methods here
}
