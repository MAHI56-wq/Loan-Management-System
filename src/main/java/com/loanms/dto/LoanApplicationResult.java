package com.loanms.dto;

import com.loanms.feature.LoanEligibilityService;
import com.loanms.feature.LoanRiskAssessment;
import com.loanms.model.Loan;

public record LoanApplicationResult(
        Loan loan,
        double monthlyPayment,
        LoanEligibilityService.EligibilityResult eligibility,
        LoanRiskAssessment riskAssessment,
        String nextStep) {
}
