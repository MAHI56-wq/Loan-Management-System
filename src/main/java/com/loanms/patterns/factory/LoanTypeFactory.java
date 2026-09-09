package com.loanms.patterns.factory;
import com.loanms.patterns.strategy.*;
public final class LoanTypeFactory {
    private LoanTypeFactory() {}
    public static InterestStrategy strategyFor(String loanType) {
        return switch (loanType.toLowerCase()) {
            case "personal" -> new SimpleInterestStrategy();
            case "business", "home" -> new ReducingBalanceStrategy();
            default -> throw new IllegalArgumentException("Unsupported loan type: " + loanType);
        };
    }
}