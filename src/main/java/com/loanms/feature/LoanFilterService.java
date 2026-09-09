package com.loanms.feature;

import com.loanms.model.Loan;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public class LoanFilterService {
    public List<Loan> filter(List<Loan> loans, LoanSearchCriteria criteria) {
        Stream<Loan> stream = loans.stream();

        if (criteria.text() != null && !criteria.text().isBlank()) {
            String term = criteria.text().toLowerCase(Locale.ROOT);
            stream = stream.filter(loan ->
                    String.valueOf(loan.id()).contains(term)
                            || loan.loanType().toLowerCase(Locale.ROOT).contains(term)
                            || loan.status().toLowerCase(Locale.ROOT).contains(term));
        }

        if (criteria.status() != null && !criteria.status().isBlank()) {
            stream = stream.filter(loan ->
                    loan.status().equalsIgnoreCase(criteria.status()));
        }

        if (criteria.loanType() != null && !criteria.loanType().isBlank()) {
            stream = stream.filter(loan ->
                    loan.loanType().equalsIgnoreCase(criteria.loanType()));
        }

        if (criteria.minPrincipal() != null) {
            stream = stream.filter(loan ->
                    loan.principal() >= criteria.minPrincipal());
        }

        if (criteria.maxPrincipal() != null) {
            stream = stream.filter(loan ->
                    loan.principal() <= criteria.maxPrincipal());
        }

        return stream.toList();
    }
}
