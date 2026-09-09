package com.loanms.model;
import java.time.LocalDateTime;
public record Loan(int id, int customerId, Integer officerId, String loanType,
                   double principal, double annualInterestRate, int termMonths,
                   String status, LocalDateTime createdAt) {}