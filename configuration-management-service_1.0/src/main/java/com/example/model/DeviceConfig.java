package com.example.model;

import com.jcraft.jsch.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;

public class DeviceConfig {

    private String ipAddress;
    private String username;
    private String password;

    public DeviceConfig(String ipAddress, String username, String password) {
        this.ipAddress = ipAddress;
        this.username = username;
        this.password = password;
    }

    /**
     * Connect to the device via SSH and execute the given command.
     * @param command The command to execute on the device.
     * @return The output from the device after executing the command.
     * @throws JSchException If there is an error during SSH connection.
     * @throws Exception If there is an error during command execution.
     */
    private String executeCommand(String command) throws JSchException, Exception {
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, ipAddress, 22);
        session.setPassword(password);

        // Bypass the host key check for simplicity (not recommended for production)
        session.setConfig("StrictHostKeyChecking", "no");

        session.connect();

        ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
        channelExec.setCommand(command);

        InputStream in = channelExec.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        channelExec.connect();

        byte[] buffer = new byte[1024];
        int readCount;

        while ((readCount = in.read(buffer)) != -1) {
            baos.write(buffer, 0, readCount);
        }

        String result = new String(baos.toByteArray());
        channelExec.disconnect();
        session.disconnect();

        return result;
    }

    /**
     * Backup the current configuration of the device.
     * @return The output confirming the backup operation.
     * @throws JSchException If there is an error during SSH connection.
     * @throws Exception If there is an error during command execution.
     */
    public String backupConfig() throws JSchException, Exception {
        String backupCommand = "show running-config";
        return executeCommand(backupCommand);
    }

    /**
     * Restore the configuration to the device.
     * @param configData The configuration data to be restored.
     * @return The output confirming the restore operation.
     * @throws JSchException If there is an error during SSH connection.
     * @throws Exception If there is an error during command execution.
     */
    public String restoreConfig(String configData) throws JSchException, Exception {
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, ipAddress, 22);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");

        session.connect();

        ChannelShell channelShell = (ChannelShell) session.openChannel("shell");
        OutputStream out = channelShell.getOutputStream();
        InputStream in = channelShell.getInputStream();

        channelShell.connect();

        out.write((configData + "\n").getBytes());
        out.flush();

        // Read output from the command
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int readCount;
        while ((readCount = in.read(buffer)) != -1) {
            baos.write(buffer, 0, readCount);
        }

        String result = new String(baos.toByteArray());

        channelShell.disconnect();
        session.disconnect();

        return result;
    }

    /**
     * Push configuration changes to the device.
     * @param configCommands The commands to execute on the device.
     * @return The output confirming the push operation.
     * @throws JSchException If there is an error during SSH connection.
     * @throws Exception If there is an error during command execution.
     */
    public String pushConfig(String configCommands) throws JSchException, Exception {
        return executeCommand(configCommands);
    }

    /**
     * Track changes in the configuration by comparing the running and startup configurations.
     * @return The differences between the running and startup configurations.
     * @throws JSchException If there is an error during SSH connection.
     * @throws Exception If there is an error during command execution.
     */
    public String trackConfigChanges() throws JSchException, Exception {
        String command = "show archive config differences";
        return executeCommand(command);
    }

    /**
     * Execute a custom command on the device.
     * @param command The custom command to execute.
     * @return The output from the device after executing the command.
     * @throws JSchException If there is an error during SSH connection.
     * @throws Exception If there is an error during command execution.
     */
    public String executeCustomCommand(String command) throws JSchException, Exception {
        return executeCommand(command);
    }
}
