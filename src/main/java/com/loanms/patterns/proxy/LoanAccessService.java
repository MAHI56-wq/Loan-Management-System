package com.loanms.patterns.proxy;

import com.loanms.model.Loan;
import com.loanms.service.LoanService;

public class LoanAccessService implements LoanAccess {
    private final LoanService service = new LoanService();

    public Loan viewLoan(int loanId, String role) {
        return service.get(loanId);
    }
}
