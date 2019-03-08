package com.epam.logistics.base.util.generator.impl;

public enum PriorityName {
    NORMAL(0),
    PERISHABLE_GOODS(1);

    int priority;

    PriorityName(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
