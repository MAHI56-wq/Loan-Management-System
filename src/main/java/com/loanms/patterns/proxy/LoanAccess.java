package com.loanms.patterns.proxy;

import com.loanms.model.Loan;

public interface LoanAccess {
    Loan viewLoan(int loanId, String role);
}
