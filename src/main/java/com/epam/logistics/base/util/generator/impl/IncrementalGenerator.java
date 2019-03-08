package com.epam.logistics.base.util.generator.impl;

import com.epam.logistics.base.util.generator.Generator;

public class IncrementalGenerator implements Generator<Integer> {

    Integer previous;

    public IncrementalGenerator(Integer initial) {
        this.previous = initial;
    }

    @Override
    public Integer generateNext() {
        return previous++;
    }
}
