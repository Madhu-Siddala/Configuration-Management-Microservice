package com.example.model;

public class ChangeLog {
    private String timestamp;
    private String action;
    private String details;

    // Default constructor
    public ChangeLog() {
    }

    // Parameterized constructor
    public ChangeLog(String timestamp, String action, String details) {
        this.timestamp = timestamp;
        this.action = action;
        this.details = details;
    }

    // Getters and Setters
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "ChangeLog{" +
                "timestamp='" + timestamp + '\'' +
                ", action='" + action + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
