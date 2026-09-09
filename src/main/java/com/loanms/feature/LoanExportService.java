package com.loanms.feature;

import com.loanms.model.Loan;
import java.util.List;

public class LoanExportService {

    public String toCsv(List<Loan> loans) {

        StringBuilder csv = new StringBuilder();

        csv.append("ID,Customer ID,Officer ID,Type,Principal,Rate,Months,Status,Created At\n");

        for (Loan loan : loans) {

            csv.append(loan.id())
                    .append(',')
                    .append(loan.customerId())
                    .append(',')
                    .append(loan.officerId() == null ? "" : loan.officerId())
                    .append(',')
                    .append(escape(loan.loanType()))
                    .append(',')
                    .append(loan.principal())
                    .append(',')
                    .append(loan.annualInterestRate())
                    .append(',')
                    .append(loan.termMonths())
                    .append(',')
                    .append(escape(loan.status()))
                    .append(',')
                    .append(loan.createdAt())
                    .append('\n');
        }

        return csv.toString();
    }

    private String escape(String value) {

        if (value == null) {
            return "";
        }

        // Replace " with ""
        String escaped = value.replace("\"", "\"\"");

        // If value contains comma or double quote,
        // wrap the whole value with double quotes.
        if (escaped.contains(",") || escaped.contains("\"")) {
            return "\"" + escaped + "\"";
        }

        return escaped;
    }
}