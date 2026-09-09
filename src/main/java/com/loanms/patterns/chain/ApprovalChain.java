package com.loanms.patterns.chain;

public final class ApprovalChain {
    private ApprovalChain() { }

    public static ApprovalHandler createDefault() {
        ApprovalHandler junior = new JuniorOfficerHandler();
        ApprovalHandler senior = junior.setNext(new SeniorOfficerHandler());
        senior.setNext(new ManagerHandler());
        return junior;
    }
}
