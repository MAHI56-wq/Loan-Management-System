package com.loanms.patterns.chain;

public class ManagerHandler extends ApprovalHandler {
    protected boolean canHandle(LoanApprovalRequest request) {
        return request.getAmount() <= 1000000 && request.getRiskScore() < 70;
    }

    protected void process(LoanApprovalRequest request) {
        request.approve("Approved by Branch Manager");
    }
}
