package com.loanms.feature;

public record LoanSearchCriteria(
        String text,
        String status,
        String loanType,
        Double minPrincipal,
        Double maxPrincipal) {

    public boolean hasFilters() {
        return (text != null && !text.isBlank())
                || (status != null && !status.isBlank())
                || (loanType != null && !loanType.isBlank())
                || minPrincipal != null
                || maxPrincipal != null;
    }
}
