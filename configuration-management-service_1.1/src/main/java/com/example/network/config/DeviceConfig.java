package com.example.network.config;

import com.jcraft.jsch.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class DeviceConfig {

    private String ipAddress;
    private String username;
    private String password;

    public DeviceConfig(String ipAddress, String username, String password) {
        this.ipAddress = ipAddress;
        this.username = username;
        this.password = password;
    }

    private Session createSession() throws JSchException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, ipAddress, 22);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        return session;
    }

    private String executeCommand(Session session, String command) throws Exception {
        ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
        ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
        channelExec.setOutputStream(responseStream);
        channelExec.setCommand(command);
        channelExec.connect();

        InputStream in = channelExec.getInputStream();
        byte[] tmp = new byte[1024];
        while (true) {
            while (in.available() > 0) {
                int i = in.read(tmp, 0, 1024);
                if (i < 0) break;
                responseStream.write(tmp, 0, i);
            }
            if (channelExec.isClosed()) {
                if (in.available() > 0) continue;
                break;
            }
            Thread.sleep(1000);
        }

        channelExec.disconnect();
        return new String(responseStream.toByteArray(), StandardCharsets.UTF_8);
    }

    public String backupConfig() throws Exception {
        Session session = null;
        try {
            session = createSession();
            return executeCommand(session, "show running-config");
        } finally {
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
    }

    public String restoreConfig(String configData) throws Exception {
        Session session = null;
        try {
            session = createSession();
            return executeCommand(session, "configure terminal\n" + configData + "\nend");
        } finally {
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
    }

    public String pushConfig(String configCommands) throws Exception {
        Session session = null;
        try {
            session = createSession();
            return executeCommand(session, "configure terminal\n" + configCommands + "\nend");
        } finally {
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
    }

    public String trackConfigChanges() throws Exception {
        Session session = null;
        try {
            session = createSession();
            return executeCommand(session, "show archive config differences");
        } finally {
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
    }

    public String executeCustomCommand(String command) throws Exception {
        Session session = null;
        try {
            session = createSession();
            return executeCommand(session, command);
        } finally {
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
    }
}
