package com.loanms.patterns.chain;

public class SeniorOfficerHandler extends ApprovalHandler {
    protected boolean canHandle(LoanApprovalRequest request) {
        return request.getAmount() <= 500000 && request.getRiskScore() < 55;
    }

    protected void process(LoanApprovalRequest request) {
        request.approve("Approved by Senior Officer");
    }
}
