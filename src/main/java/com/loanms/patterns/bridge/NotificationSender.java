package com.loanms.patterns.bridge;

public interface NotificationSender {
    void send(String destination, String message);
}
