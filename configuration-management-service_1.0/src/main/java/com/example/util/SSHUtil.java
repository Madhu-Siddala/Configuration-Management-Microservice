package com.example.util;

import com.jcraft.jsch.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SSHUtil {

    public static String executeCommand(String ipAddress, String username, String password, String command) {
        StringBuilder output = new StringBuilder();
        
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(username, ipAddress, 22);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(channel.getInputStream()));
            channel.connect();
            
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            
            channel.disconnect();
            session.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error executing command: " + e.getMessage();
        }
        
        return output.toString();
    }
}
