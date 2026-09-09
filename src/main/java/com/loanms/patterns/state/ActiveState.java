package com.loanms.patterns.state;
public class ActiveState implements LoanState {
    public String name(){return "Active";}
    public LoanState approve(){throw new IllegalStateException("Already active");}
    public LoanState reject(){throw new IllegalStateException("Active loan cannot be rejected");}
    public LoanState paymentMade(boolean fullyPaid){return fullyPaid ? new CompletedState() : this;}
}