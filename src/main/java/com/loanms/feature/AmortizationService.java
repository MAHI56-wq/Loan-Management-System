package com.loanms.feature;

import java.util.ArrayList;
import java.util.List;

public class AmortizationService {

    public List<PaymentPlan> buildPlan(
            double principal,
            double annualRate,
            int months) {

        List<PaymentPlan> plans = new ArrayList<>();

        if (principal <= 0 || months <= 0) {
            return plans;
        }

        double monthlyRate = annualRate / 1200.0;
        double monthlyPayment;

        if (monthlyRate == 0) {
            monthlyPayment = principal / months;
        } else {
            monthlyPayment = principal * monthlyRate /
                    (1 - Math.pow(1 + monthlyRate, -months));
        }

        double balance = principal;

        for (int month = 1; month <= months; month++) {
            double interest = balance * monthlyRate;
            double principalPaid = monthlyPayment - interest;

            if (month == months || principalPaid > balance) {
                principalPaid = balance;
                monthlyPayment = principalPaid + interest;
            }

            double closing = Math.max(0, balance - principalPaid);

            plans.add(new PaymentPlan(
                    month,
                    balance,
                    monthlyPayment,
                    interest,
                    principalPaid,
                    closing));

            balance = closing;
        }

        return plans;
    }
}
