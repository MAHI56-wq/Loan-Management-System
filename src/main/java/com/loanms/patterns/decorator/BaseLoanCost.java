package com.loanms.patterns.decorator;

public class BaseLoanCost implements LoanCost {
    private final double principal;

    public BaseLoanCost(double principal) {
        this.principal = Math.max(0, principal);
    }

    public double totalCost() { return principal; }
    public String description() { return "Principal"; }
}
