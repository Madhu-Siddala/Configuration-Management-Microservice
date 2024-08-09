package com.example.sshdemo.controller;


import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.InputStream;

@Controller
public class RouterController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/run-command")
    public String runCommand(@RequestParam("command") String command, Model model) {
        String host = "172.20.0.3";
        String user = "admin";
        String password = "wipro";
        int port = 22;

        JSch jsch = new JSch();
        Session session = null;
        StringBuilder output = new StringBuilder();

        try {
            session = jsch.getSession(user, host, port);
            session.setPassword(password);

            // Disable strict host key checking
            session.setConfig("StrictHostKeyChecking", "no");

            session.connect();

            // Execute the command on the remote router
            ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
            channelExec.setCommand(command);

            InputStream in = channelExec.getInputStream();
            channelExec.connect();

            byte[] buffer = new byte[1024];
            int readCount;
            while ((readCount = in.read(buffer)) > 0) {
                output.append(new String(buffer, 0, readCount));
            }

            channelExec.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error occurred: " + e.getMessage());
            return "index";
        } finally {
            if (session != null) {
                session.disconnect();
            }
        }

        model.addAttribute("output", output.toString());
        return "result";
    }
}

