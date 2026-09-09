package com.loanms.patterns.singleton;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class AuditLogger {
    private static final AuditLogger INSTANCE = new AuditLogger();
    private final List<String> entries = new ArrayList<>();

    private AuditLogger() { }

    public static AuditLogger getInstance() {
        return INSTANCE;
    }

    public synchronized void log(String action) {
        entries.add(LocalDateTime.now() + " | " + action);
    }

    public synchronized List<String> entries() {
        return Collections.unmodifiableList(new ArrayList<>(entries));
    }

    public synchronized void clear() {
        entries.clear();
    }
}
