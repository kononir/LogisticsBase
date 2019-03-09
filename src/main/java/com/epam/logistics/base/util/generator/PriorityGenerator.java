package com.epam.logistics.base.util.generator;

import com.epam.logistics.base.util.generator.exception.IllegalPriorityNameException;
import com.epam.logistics.base.util.generator.impl.PriorityName;

public interface PriorityGenerator<T> {
    T generateNext(PriorityName priorityName) throws IllegalPriorityNameException;
}
