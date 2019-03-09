package com.epam.logistics.base.util.generator.impl;

import com.epam.logistics.base.util.generator.Generator;
import com.epam.logistics.base.util.generator.PriorityGenerator;
import com.epam.logistics.base.util.generator.exception.IllegalPriorityNameException;

import java.util.List;

public class IncrementalPriorityGenerator implements PriorityGenerator<Integer> {
    private List<Generator<Integer>> simpleGenerators;

    public IncrementalPriorityGenerator(List<Generator<Integer>> simpleGenerators) {
        this.simpleGenerators = simpleGenerators;
    }

    @Override
    public Integer generateNext(PriorityName priorityName) throws IllegalPriorityNameException {
        if (priorityName == null) {
            throw new IllegalPriorityNameException("Priority name is not null!");
        }

        Generator<Integer> simpleGenerator = simpleGenerators.get(priorityName.getPriority());

        return simpleGenerator.generateNext();
    }
}
