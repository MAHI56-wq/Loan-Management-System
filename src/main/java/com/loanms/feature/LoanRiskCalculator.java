package com.loanms.feature;

public class LoanRiskCalculator {

    public LoanRiskAssessment assess(
            double principal,
            double annualRate,
            int termMonths,
            int completedLoans,
            int latePayments) {

        int score = 0;

        if (principal > 500000) score += 35;
        else if (principal > 200000) score += 20;
        else score += 10;

        if (annualRate > 18) score += 20;
        else if (annualRate > 12) score += 10;

        if (termMonths > 60) score += 20;
        else if (termMonths > 36) score += 10;

        score += Math.min(latePayments * 8, 25);
        score -= Math.min(completedLoans * 5, 15);
        score = Math.max(0, Math.min(score, 100));

        if (score >= 60) {
            return new LoanRiskAssessment(score, LoanRiskLevel.HIGH,
                    "High amount, long term, or weak repayment history");
        }

        if (score >= 30) {
            return new LoanRiskAssessment(score, LoanRiskLevel.MEDIUM,
                    "Moderate loan exposure requires officer review");
        }

        return new LoanRiskAssessment(score, LoanRiskLevel.LOW,
                "Application is within the normal risk range");
    }
}
