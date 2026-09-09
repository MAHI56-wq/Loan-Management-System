package com.loanms.patterns.observer;
public interface LoanObserver { void onLoanChanged(int loanId, String message); }