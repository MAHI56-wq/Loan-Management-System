package com.loanms.patterns.strategy;
public class SimpleInterestStrategy implements InterestStrategy {
    public double calculateInterest(double principal, double annualRate, int months) {
        return principal * (annualRate / 100.0) * (months / 12.0);
    }
}