package com.epam.logistics.base.util.generator;

import com.epam.logistics.base.util.generator.exception.IllegalPriorityNameException;
import com.epam.logistics.base.util.generator.impl.Priority;

public interface PriorityGenerator<T> {
    T generateNext(Priority priority) throws IllegalPriorityNameException;
}
