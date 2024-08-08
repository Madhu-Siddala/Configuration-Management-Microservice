package com.example.demo;

import java.io.OutputStream;
import java.io.PrintStream;

import com.jcraft.jsch.*;

public class RouterConfig {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "wipro";
    private static final String HOST = "172.20.0.2";
    private static final int PORT = 22;

    public static void main(String[] args) {
        JSch jsch = new JSch();
        Session session = null;
        Channel channel = null;
        try {
            session = jsch.getSession(USERNAME, HOST, PORT);
            session.setPassword(PASSWORD);
            
            // Avoid asking for key confirmation
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            channel = session.openChannel("shell");
            channel.setInputStream(System.in);
            channel.setOutputStream(System.out);
            
            // Set up input stream for sending commands
            OutputStream out = channel.getOutputStream();
            PrintStream ps = new PrintStream(out, true);

            channel.connect();

            // Send commands to the router
            ps.println("configure terminal");
            ps.println("hostname madhu");
            ps.println("end");
            ps.println("write memory");
            ps.println("exit");

            // Give some time for commands to execute
            Thread.sleep(5000);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (channel != null && channel.isConnected()) {
                channel.disconnect();
            }
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
    }
}