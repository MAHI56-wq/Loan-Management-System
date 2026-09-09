package com.loanms.patterns.bridge;

public class ApprovalNotification extends LoanNotification {
    public ApprovalNotification(NotificationSender sender) {
        super(sender);
    }

    public void notifyCustomer(String destination, int loanId) {
        sender.send(destination, "Your loan #" + loanId + " has been approved.");
    }
}
