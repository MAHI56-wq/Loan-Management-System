package com.loanms.feature;

import com.loanms.model.Loan;
import java.util.List;

public record LoanDashboard(
        int totalLoans,
        int pendingLoans,
        int activeLoans,
        int completedLoans,
        int rejectedLoans,
        double totalPrincipal,
        double averagePrincipal) {

    public static LoanDashboard from(List<Loan> loans) {
        int pending = 0;
        int active = 0;
        int completed = 0;
        int rejected = 0;
        double total = 0;

        for (Loan loan : loans) {
            total += loan.principal();

            switch (loan.status()) {
                case "Pending" -> pending++;
                case "Active" -> active++;
                case "Completed" -> completed++;
                case "Rejected" -> rejected++;
                default -> { }
            }
        }

        double average = loans.isEmpty() ? 0 : total / loans.size();

        return new LoanDashboard(
                loans.size(), pending, active, completed, rejected,
                total, average);
    }
}
