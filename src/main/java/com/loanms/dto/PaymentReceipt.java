package com.loanms.dto;

import java.time.LocalDateTime;

public record PaymentReceipt(
        int loanId,
        double amount,
        double totalPaid,
        double remainingAmount,
        String status,
        LocalDateTime recordedAt) {

    public String summary() {
        return "Loan #" + loanId
                + " | Payment: " + amount
                + " | Remaining: " + remainingAmount
                + " | Status: " + status;
    }
}
