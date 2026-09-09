package com.loanms.patterns.mediator;

public interface LoanEventMediator {
    void publish(String eventType, int loanId, String message);
}
