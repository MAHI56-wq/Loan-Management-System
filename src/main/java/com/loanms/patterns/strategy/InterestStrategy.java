package com.loanms.patterns.strategy;
public interface InterestStrategy {
    double calculateInterest(double principal, double annualRate, int months);
    default double monthlyPayment(double principal, double annualRate, int months) {
        double r = annualRate / 100.0 / 12.0;
        if (r == 0) return principal / months;
        return principal * r * Math.pow(1 + r, months) / (Math.pow(1 + r, months) - 1);
    }
}