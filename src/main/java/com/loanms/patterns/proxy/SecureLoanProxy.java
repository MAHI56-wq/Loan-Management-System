package com.loanms.patterns.proxy;

import com.loanms.model.Loan;
import java.util.Set;

public class SecureLoanProxy implements LoanAccess {
    private final LoanAccess target;
    private final Set<String> allowedRoles = Set.of("ADMIN", "OFFICER", "MANAGER");

    public SecureLoanProxy(LoanAccess target) {
        this.target = target;
    }

    public Loan viewLoan(int loanId, String role) {
        if (role == null || !allowedRoles.contains(role.toUpperCase())) {
            throw new SecurityException("Role is not allowed to access loan details");
        }
        return target.viewLoan(loanId, role);
    }
}
