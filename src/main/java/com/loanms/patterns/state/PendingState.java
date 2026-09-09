package com.loanms.patterns.state;
public class PendingState implements LoanState {
    public String name(){return "Pending";}
    public LoanState approve(){return new ActiveState();}
    public LoanState reject(){return new RejectedState();}
    public LoanState paymentMade(boolean fullyPaid){throw new IllegalStateException("Pending loan cannot receive payment");}
}