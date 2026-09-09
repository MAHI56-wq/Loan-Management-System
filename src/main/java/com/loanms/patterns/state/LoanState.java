package com.loanms.patterns.state;
public interface LoanState {
    String name();
    LoanState approve();
    LoanState reject();
    LoanState paymentMade(boolean fullyPaid);
}