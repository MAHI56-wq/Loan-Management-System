package com.loanms.patterns.decorator;

public class InsuranceFeeDecorator extends LoanCostDecorator {
    private final double fee;

    public InsuranceFeeDecorator(LoanCost wrapped, double fee) {
        super(wrapped);
        this.fee = Math.max(0, fee);
    }

    public double totalCost() { return wrapped.totalCost() + fee; }
    public String description() { return wrapped.description() + " + Insurance"; }
}
