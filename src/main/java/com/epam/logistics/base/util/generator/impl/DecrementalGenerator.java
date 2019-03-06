package com.epam.logistics.base.util.generator.impl;

import com.epam.logistics.base.util.generator.Generator;

public class DecrementalGenerator implements Generator<Integer> {
    private Integer last;

    public DecrementalGenerator(Integer begin) {
        this.last = begin;
    }

    @Override
    public Integer generateNext() {
        return last--;
    }
}
