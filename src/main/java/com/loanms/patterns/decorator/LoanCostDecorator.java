package com.loanms.patterns.decorator;

public abstract class LoanCostDecorator implements LoanCost {
    protected final LoanCost wrapped;

    protected LoanCostDecorator(LoanCost wrapped) {
        this.wrapped = wrapped;
    }
}
