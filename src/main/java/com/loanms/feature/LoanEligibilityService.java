package com.loanms.feature;

public class LoanEligibilityService {

    public EligibilityResult evaluate(
            double monthlyIncome,
            double monthlyDebt,
            double requestedPayment,
            int age) {

        if (age < 18 || age > 70) {
            return new EligibilityResult(false, 0, "Applicant age is outside policy range");
        }

        if (monthlyIncome <= 0) {
            return new EligibilityResult(false, 0, "Monthly income must be positive");
        }

        double ratio = (monthlyDebt + requestedPayment) / monthlyIncome;
        int score = (int) Math.max(0, Math.min(100, 100 - ratio * 100));

        if (ratio > 0.60) {
            return new EligibilityResult(false, score,
                    "Debt-to-income ratio is above 60 percent");
        }

        if (ratio > 0.45) {
            return new EligibilityResult(true, score,
                    "Eligible but requires additional officer review");
        }

        return new EligibilityResult(true, score,
                "Applicant meets the standard affordability rule");
    }

    public record EligibilityResult(
            boolean eligible,
            int score,
            String message) {
    }
}
