package com.loanms.patterns.chain;

public class LoanApprovalRequest {
    private final int loanId;
    private final double amount;
    private final int riskScore;
    private boolean approved;
    private String decision;

    public LoanApprovalRequest(int loanId, double amount, int riskScore) {
        this.loanId = loanId;
        this.amount = amount;
        this.riskScore = riskScore;
        this.decision = "Pending review";
    }

    public int getLoanId() { return loanId; }
    public double getAmount() { return amount; }
    public int getRiskScore() { return riskScore; }
    public boolean isApproved() { return approved; }
    public String getDecision() { return decision; }

    public void approve(String decision) {
        this.approved = true;
        this.decision = decision;
    }

    public void reject(String decision) {
        this.approved = false;
        this.decision = decision;
    }
}
