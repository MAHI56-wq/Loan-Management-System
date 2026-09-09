package com.loanms.patterns.state;
public class RejectedState implements LoanState {
    public String name(){return "Rejected";}
    public LoanState approve(){throw new IllegalStateException("Rejected loan cannot be approved");}
    public LoanState reject(){throw new IllegalStateException("Already rejected");}
    public LoanState paymentMade(boolean fullyPaid){throw new IllegalStateException("Rejected loan cannot receive payment");}
}