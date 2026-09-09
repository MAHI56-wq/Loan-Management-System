package com.loanms.service;

import com.loanms.dto.LoanApplicationRequest;
import com.loanms.dto.LoanApplicationResult;
import com.loanms.dto.PaymentReceipt;
import com.loanms.feature.LoanEligibilityService;
import com.loanms.feature.LoanRiskAssessment;
import com.loanms.feature.LoanRiskCalculator;
import com.loanms.model.Loan;
import com.loanms.patterns.chain.ApprovalChain;
import com.loanms.patterns.chain.LoanApprovalRequest;
import com.loanms.patterns.mediator.LoanEventMediator;
import com.loanms.patterns.mediator.SimpleLoanMediator;
import java.time.LocalDateTime;

public class AdvancedLoanService {
    private final LoanService loanService = new LoanService();
    private final LoanEligibilityService eligibilityService = new LoanEligibilityService();
    private final LoanRiskCalculator riskCalculator = new LoanRiskCalculator();
    private final LoanEventMediator mediator = new SimpleLoanMediator();

    public LoanApplicationResult apply(LoanApplicationRequest request) {
        request.validate();

        double monthlyPayment = loanService.monthlyPayment(
                request.loanType(),
                request.principal(),
                request.annualRate(),
                request.termMonths());

        LoanEligibilityService.EligibilityResult eligibility =
                eligibilityService.evaluate(
                        request.monthlyIncome(),
                        request.monthlyDebt(),
                        monthlyPayment,
                        request.applicantAge());

        LoanRiskAssessment risk = riskCalculator.assess(
                request.principal(),
                request.annualRate(),
                request.termMonths(),
                0,
                0);

        if (!eligibility.eligible()) {
            return new LoanApplicationResult(
                    null,
                    monthlyPayment,
                    eligibility,
                    risk,
                    "Application was not saved because eligibility failed");
        }

        Loan loan = loanService.apply(
                request.customerId(),
                request.loanType(),
                request.principal(),
                request.annualRate(),
                request.termMonths());

        mediator.publish("APPLICATION", loan.id(), "New application submitted");

        return new LoanApplicationResult(
                loan,
                monthlyPayment,
                eligibility,
                risk,
                "Application created and waiting for review");
    }

    public String reviewAutomatically(int loanId) {
        Loan loan = loanService.get(loanId);

        if (loan == null) {
            throw new IllegalArgumentException("Loan not found");
        }

        LoanRiskAssessment risk = riskCalculator.assess(
                loan.principal(),
                loan.annualInterestRate(),
                loan.termMonths(),
                0,
                0);

        LoanApprovalRequest request = new LoanApprovalRequest(
                loanId,
                loan.principal(),
                risk.score());

        ApprovalChain.createDefault().handle(request);

        if (request.isApproved()) {
            loanService.approve(loanId);
            mediator.publish("APPROVAL", loanId, request.getDecision());
        } else {
            loanService.reject(loanId);
            mediator.publish("REJECTION", loanId, request.getDecision());
        }

        return request.getDecision();
    }

    public PaymentReceipt pay(int loanId, double amount) {
        Loan before = loanService.get(loanId);

        if (before == null) {
            throw new IllegalArgumentException("Loan not found");
        }

        loanService.recordPayment(loanId, amount);

        Loan after = loanService.get(loanId);
        double totalPaid = loanService.totalPaid(loanId);
        double totalDue = loanService.monthlyPayment(
                after.loanType(),
                after.principal(),
                after.annualInterestRate(),
                after.termMonths()) * after.termMonths();

        double remaining = Math.max(0, totalDue - totalPaid);

        mediator.publish("PAYMENT", loanId, "Payment recorded: " + amount);

        return new PaymentReceipt(
                loanId,
                amount,
                totalPaid,
                remaining,
                after.status(),
                LocalDateTime.now());
    }
}
