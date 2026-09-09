package com.loanms.feature;

import com.loanms.model.Loan;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FeatureServiceTest {

    @Test
    void eligibilityShouldRejectInvalidAge() {
        LoanEligibilityService service = new LoanEligibilityService();

        var result = service.evaluate(50000, 5000, 5000, 17);

        assertFalse(result.eligible());
        assertTrue(result.message().contains("age"));
    }

    @Test
    void eligibilityShouldAcceptAffordableLoan() {
        LoanEligibilityService service = new LoanEligibilityService();

        var result = service.evaluate(100000, 5000, 15000, 30);

        assertTrue(result.eligible());
        assertTrue(result.score() > 0);
    }

    @Test
    void riskShouldBeLowForSmallLoan() {
        LoanRiskCalculator calculator = new LoanRiskCalculator();

        LoanRiskAssessment assessment = calculator.assess(
                50000, 8, 12, 2, 0);

        assertEquals(LoanRiskLevel.LOW, assessment.level());
    }

    @Test
    void riskShouldBeHighForLargeLoan() {
        LoanRiskCalculator calculator = new LoanRiskCalculator();

        LoanRiskAssessment assessment = calculator.assess(
                900000, 22, 84, 0, 4);

        assertEquals(LoanRiskLevel.HIGH, assessment.level());
        assertFalse(assessment.isAcceptable());
    }

    @Test
    void amortizationShouldProduceRequestedNumberOfRows() {
        AmortizationService service = new AmortizationService();

        List<PaymentPlan> plans = service.buildPlan(100000, 12, 12);

        assertEquals(12, plans.size());
        assertEquals(100000, plans.get(0).openingBalance(), 0.01);
        assertEquals(0, plans.get(11).closingBalance(), 0.01);
    }

    @Test
    void amortizationShouldHandleZeroInterest() {
        AmortizationService service = new AmortizationService();

        List<PaymentPlan> plans = service.buildPlan(12000, 0, 12);

        assertEquals(1000, plans.get(0).payment(), 0.01);
    }

    @Test
    void filterShouldFindActiveLoans() {
        LoanFilterService service = new LoanFilterService();

        List<Loan> loans = sampleLoans();

        List<Loan> result = service.filter(loans,
                new LoanSearchCriteria(null, "Active", null, null, null));

        assertEquals(1, result.size());
        assertEquals("Active", result.get(0).status());
    }

    @Test
    void filterShouldApplyAmountRange() {
        LoanFilterService service = new LoanFilterService();

        List<Loan> result = service.filter(sampleLoans(),
                new LoanSearchCriteria(null, null, null, 100000.0, 300000.0));

        assertEquals(1, result.size());
        assertEquals(200000, result.get(0).principal());
    }

    @Test
    void dashboardShouldCountStatuses() {
        LoanDashboard dashboard = LoanDashboard.from(sampleLoans());

        assertEquals(3, dashboard.totalLoans());
        assertEquals(1, dashboard.pendingLoans());
        assertEquals(1, dashboard.activeLoans());
        assertEquals(1, dashboard.completedLoans());
    }

    private List<Loan> sampleLoans() {
        LocalDateTime now = LocalDateTime.now();

        return List.of(
                new Loan(1, 1, 1, "Personal", 50000, 12, 12, "Pending", now),
                new Loan(2, 1, 1, "Home", 200000, 9, 120, "Active", now),
                new Loan(3, 2, 2, "Business", 400000, 14, 60, "Completed", now));
    }
}
