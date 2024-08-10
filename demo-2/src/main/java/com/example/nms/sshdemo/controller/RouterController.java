package com.example.nms.sshdemo.controller;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ChannelExec;
import org.apache.sshd.client.config.keys.ClientIdentityLoader;
import org.apache.sshd.client.keyverifier.KnownHostsServerKeyVerifier;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.FactoryManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class RouterController {

    @Value("${commands.file.path}")
    private String commandsFilePath;

    @Value("${known.hosts.file.path}")
    private String knownHostsFilePath;

    @PostMapping("/run-command")
    public String runCommand(Model model) {
        String host = "172.20.0.2";
        String user = "admin";
        String password = "wipro";
        StringBuilder output = new StringBuilder();

        try (SshClient client = SshClient.setUpDefaultClient()) {
            // Initialize the KnownHostsServerKeyVerifier
            Path knownHostsPath = Paths.get(knownHostsFilePath);
            KnownHostsServerKeyVerifier keyVerifier = new KnownHostsServerKeyVerifier(new PathResource(knownHostsPath));
            try (ClientSession session = client.connect(user, host, 22).verify(30000).getSession()) {
                session.addPasswordIdentity(password);
                session.auth().verify(60000);

                // Reading commands from the file
                InputStream is = getClass().getClassLoader().getResourceAsStream(commandsFilePath);
                if (is == null) {
                    model.addAttribute("error", "Commands file not found.");
                    return "index";
                }

                try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                    String command;
                    while ((command = br.readLine()) != null) {
                        try (ChannelExec channel = session.createExecChannel(command)) {
                            channel.open().verify(30000);

                            try (InputStream in = channel.getInvertedOut()) {
                                String result = new String(in.readAllBytes(), StandardCharsets.UTF_8);
                                output.append("Command: ").append(command).append("\n");
                                output.append("Output: ").append(result).append("\n");
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace(); // Log the full stack trace
            model.addAttribute("error", "Error occurred: " + e.getMessage());
            return "index";
        }

        model.addAttribute("output", output.toString());
        return "result";
    }
}