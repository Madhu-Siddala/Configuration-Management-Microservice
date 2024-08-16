package com.example.demo.util;


import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.client.simple.SimpleSessionClient;

import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class SSHExecutor {

    private final String host;
    private final String username;
    private final String password;

    public SSHExecutor(String host, String username, String password) {
        this.host = host;
        this.username = username;
        this.password = password;
    }

    public String executeCommandsFromXml(String xmlFilePath) {
        try {
            SshClient client = SshClient.setUpDefaultClient();
            client.start();

            try (ClientSession session = SimpleSessionClient.clientSession(client, host, username, password)) {
                List<String> commands = loadCommandsFromXml(xmlFilePath);

                StringBuilder resultBuilder = new StringBuilder();
                for (String command : commands) {
                    resultBuilder.append("Executing command: ").append(command).append("\n");
                    String commandResult = session.executeRemoteCommand(command);
                    resultBuilder.append(commandResult).append("\n");
                }
                return resultBuilder.toString();
            } finally {
                client.stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error executing commands";
        }
    }

    private List<String> loadCommandsFromXml(String xmlFilePath) throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(new File(xmlFilePath));
        doc.getDocumentElement().normalize();

        NodeList nodeList = doc.getElementsByTagName("command");
        return IntStream.range(0, nodeList.getLength())
                        .mapToObj(i -> (Element) nodeList.item(i))
                        .map(Element::getTextContent)
                        .collect(Collectors.toList());
    }
}

