package com.loanms.patterns;

import com.loanms.patterns.bridge.ApprovalNotification;
import com.loanms.patterns.bridge.NotificationSender;
import com.loanms.patterns.chain.ApprovalChain;
import com.loanms.patterns.chain.LoanApprovalRequest;
import com.loanms.patterns.decorator.BaseLoanCost;
import com.loanms.patterns.decorator.InsuranceFeeDecorator;
import com.loanms.patterns.decorator.LoanCost;
import com.loanms.patterns.decorator.ProcessingFeeDecorator;
import com.loanms.patterns.prototype.LoanOffer;
import com.loanms.patterns.prototype.LoanOfferRegistry;
import com.loanms.patterns.singleton.AuditLogger;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExtendedPatternsTest {

    @Test
    void chainShouldApproveSmallLowRiskLoan() {
        LoanApprovalRequest request = new LoanApprovalRequest(1, 50000, 10);

        ApprovalChain.createDefault().handle(request);

        assertTrue(request.isApproved());
        assertTrue(request.getDecision().contains("Junior"));
    }

    @Test
    void chainShouldApproveMediumLoan() {
        LoanApprovalRequest request = new LoanApprovalRequest(2, 250000, 40);

        ApprovalChain.createDefault().handle(request);

        assertTrue(request.isApproved());
        assertTrue(request.getDecision().contains("Senior"));
    }

    @Test
    void chainShouldRejectVeryRiskyLoan() {
        LoanApprovalRequest request = new LoanApprovalRequest(3, 900000, 95);

        ApprovalChain.createDefault().handle(request);

        assertFalse(request.isApproved());
    }

    @Test
    void decoratorShouldAddAllFees() {
        LoanCost cost = new BaseLoanCost(100000);
        cost = new ProcessingFeeDecorator(cost, 2000);
        cost = new InsuranceFeeDecorator(cost, 1500);

        assertEquals(103500, cost.totalCost(), 0.01);
        assertTrue(cost.description().contains("Insurance"));
    }

    @Test
    void prototypeShouldCreateIndependentCopy() {
        LoanOfferRegistry registry = new LoanOfferRegistry();

        LoanOffer original = registry.createCopy("Personal");
        LoanOffer copy = original.copy();
        copy.setInterestRate(10);

        assertEquals(12, original.getInterestRate(), 0.01);
        assertEquals(10, copy.getInterestRate(), 0.01);
    }

    @Test
    void bridgeShouldDelegateToSender() {
        List<String> messages = new ArrayList<>();

        NotificationSender sender = (destination, message) ->
                messages.add(destination + "|" + message);

        new ApprovalNotification(sender).notifyCustomer("01700000000", 12);

        assertEquals(1, messages.size());
        assertTrue(messages.get(0).contains("approved"));
    }

    @Test
    void singletonShouldReturnSameLogger() {
        AuditLogger first = AuditLogger.getInstance();
        AuditLogger second = AuditLogger.getInstance();

        assertSame(first, second);
    }

    @Test
    void auditLoggerShouldStoreEntries() {
        AuditLogger logger = AuditLogger.getInstance();
        logger.clear();

        logger.log("Test event");

        assertEquals(1, logger.entries().size());
        assertTrue(logger.entries().get(0).contains("Test event"));
    }
}
