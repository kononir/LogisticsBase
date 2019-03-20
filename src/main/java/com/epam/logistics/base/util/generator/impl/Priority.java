package com.epam.logistics.base.util.generator.impl;

public enum Priority {
    NORMAL(0),
    PERISHABLE_GOODS(1);

    int priority;

    Priority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
