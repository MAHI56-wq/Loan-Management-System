package com.loanms.patterns.chain;

public abstract class ApprovalHandler {
    private ApprovalHandler next;

    public ApprovalHandler setNext(ApprovalHandler next) {
        this.next = next;
        return next;
    }

    public final void handle(LoanApprovalRequest request) {
        if (canHandle(request)) {
            process(request);
            return;
        }

        if (next != null) {
            next.handle(request);
        } else {
            request.reject("No approval authority could process the request");
        }
    }

    protected abstract boolean canHandle(LoanApprovalRequest request);
    protected abstract void process(LoanApprovalRequest request);
}
