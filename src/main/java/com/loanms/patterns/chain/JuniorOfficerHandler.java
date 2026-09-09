package com.loanms.patterns.chain;

public class JuniorOfficerHandler extends ApprovalHandler {
    protected boolean canHandle(LoanApprovalRequest request) {
        return request.getAmount() <= 100000 && request.getRiskScore() < 30;
    }

    protected void process(LoanApprovalRequest request) {
        request.approve("Approved by Junior Officer");
    }
}
