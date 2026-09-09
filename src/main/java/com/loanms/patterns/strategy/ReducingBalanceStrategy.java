package com.loanms.patterns.strategy;
public class ReducingBalanceStrategy implements InterestStrategy {
    public double calculateInterest(double principal, double annualRate, int months) {
        double payment = monthlyPayment(principal, annualRate, months);
        return payment * months - principal;
    }
}