package com.loanms.patterns.bridge;

public class SmsSender implements NotificationSender {
    public void send(String destination, String message) {
        System.out.println("SMS to " + destination + ": " + message);
    }
}
