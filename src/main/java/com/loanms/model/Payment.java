package com.loanms.model;
import java.time.LocalDateTime;
public record Payment(int id, int loanId, double amount, LocalDateTime paidAt) {}