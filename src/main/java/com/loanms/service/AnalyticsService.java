package com.loanms.service;

import com.loanms.feature.LoanDashboard;
import com.loanms.model.Loan;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnalyticsService {
    private final LoanService loanService = new LoanService();

    public LoanDashboard dashboard() {
        return LoanDashboard.from(loanService.list());
    }

    public Map<String, Long> loansByType() {
        return loanService.list().stream()
                .collect(Collectors.groupingBy(
                        Loan::loanType,
                        Collectors.counting()));
    }

    public List<Loan> largestLoans(int limit) {
        return loanService.list().stream()
                .sorted(Comparator.comparingDouble(Loan::principal).reversed())
                .limit(Math.max(0, limit))
                .toList();
    }

    public List<Loan> pendingLoans() {
        return loanService.list().stream()
                .filter(loan -> "Pending".equalsIgnoreCase(loan.status()))
                .toList();
    }
}
