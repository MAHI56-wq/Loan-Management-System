package com.loanms.patterns.bridge;

public class EmailSender implements NotificationSender {
    public void send(String destination, String message) {
        System.out.println("EMAIL to " + destination + ": " + message);
    }
}
