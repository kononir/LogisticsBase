package com.epam.logistics.base.util.generator.impl;

public enum Priority {
    NORMAL(0),
    PERISHABLE_GOODS(1);

    int value;

    Priority(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
