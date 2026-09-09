package com.loanms.feature;

public record LoanRiskAssessment(
        int score,
        LoanRiskLevel level,
        String reason) {

    public boolean isAcceptable() {
        return level != LoanRiskLevel.HIGH;
    }
}
