package com.loanms.patterns.mediator;

import com.loanms.patterns.singleton.AuditLogger;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SimpleLoanMediator implements LoanEventMediator {
    private final List<Consumer<String>> listeners = new ArrayList<>();

    public void addListener(Consumer<String> listener) {
        listeners.add(listener);
    }

    public void publish(String eventType, int loanId, String message) {
        String event = eventType + " | Loan #" + loanId + " | " + message;
        AuditLogger.getInstance().log(event);
        listeners.forEach(listener -> listener.accept(event));
    }
}
