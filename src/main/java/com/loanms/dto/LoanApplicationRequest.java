package com.loanms.dto;

public record LoanApplicationRequest(
        int customerId,
        String loanType,
        double principal,
        double annualRate,
        int termMonths,
        double monthlyIncome,
        double monthlyDebt,
        int applicantAge) {

    public void validate() {
        if (customerId <= 0) {
            throw new IllegalArgumentException("Customer ID is required");
        }
        if (loanType == null || loanType.isBlank()) {
            throw new IllegalArgumentException("Loan type is required");
        }
        if (principal <= 0) {
            throw new IllegalArgumentException("Principal must be positive");
        }
        if (annualRate < 0) {
            throw new IllegalArgumentException("Interest rate cannot be negative");
        }
        if (termMonths <= 0) {
            throw new IllegalArgumentException("Term must be positive");
        }
    }
}
