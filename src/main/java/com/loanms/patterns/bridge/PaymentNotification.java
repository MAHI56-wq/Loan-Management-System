package com.loanms.patterns.bridge;

public class PaymentNotification extends LoanNotification {
    public PaymentNotification(NotificationSender sender) {
        super(sender);
    }

    public void notifyCustomer(String destination, int loanId) {
        sender.send(destination, "Payment received for loan #" + loanId + ".");
    }
}
