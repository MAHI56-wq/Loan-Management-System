package com.loanms.patterns.bridge;

public abstract class LoanNotification {
    protected final NotificationSender sender;

    protected LoanNotification(NotificationSender sender) {
        this.sender = sender;
    }

    public abstract void notifyCustomer(String destination, int loanId);
}
