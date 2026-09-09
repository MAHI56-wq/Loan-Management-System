package com.loanms.patterns.prototype;

public class LoanOffer implements Cloneable {
    private String name;
    private double interestRate;
    private int maxMonths;
    private double processingFee;

    public LoanOffer(String name, double interestRate, int maxMonths, double processingFee) {
        this.name = name;
        this.interestRate = interestRate;
        this.maxMonths = maxMonths;
        this.processingFee = processingFee;
    }

    public String getName() { return name; }
    public double getInterestRate() { return interestRate; }
    public int getMaxMonths() { return maxMonths; }
    public double getProcessingFee() { return processingFee; }

    public void setName(String name) { this.name = name; }
    public void setInterestRate(double interestRate) { this.interestRate = interestRate; }
    public void setMaxMonths(int maxMonths) { this.maxMonths = maxMonths; }
    public void setProcessingFee(double processingFee) { this.processingFee = processingFee; }

    public LoanOffer copy() {
        try {
            return (LoanOffer) clone();
        } catch (CloneNotSupportedException exception) {
            throw new AssertionError(exception);
        }
    }
}
