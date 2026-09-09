package com.loanms.patterns.state;
public class CompletedState implements LoanState {
    public String name(){return "Completed";}
    public LoanState approve(){throw new IllegalStateException("Completed loan cannot be approved");}
    public LoanState reject(){throw new IllegalStateException("Completed loan cannot be rejected");}
    public LoanState paymentMade(boolean fullyPaid){throw new IllegalStateException("Loan already completed");}
}