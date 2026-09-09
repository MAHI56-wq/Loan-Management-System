package com.loanms.feature;

public record PaymentPlan(
        int installmentNumber,
        double openingBalance,
        double payment,
        double interest,
        double principalPaid,
        double closingBalance) {
}
