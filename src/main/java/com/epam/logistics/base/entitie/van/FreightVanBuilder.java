package com.epam.logistics.base.entitie.van;

import com.epam.logistics.base.state.van.FreightVanState;
import com.epam.logistics.base.state.van.FreightVanStateCreator;
import com.epam.logistics.base.util.generator.Generator;
import com.epam.logistics.base.util.generator.impl.IncrementalGenerator;

public class FreightVanBuilder {
    private static final int GENERATOR_INITIAL_ZERO = 0;
    private static final Generator<Integer> idGenerator = new IncrementalGenerator(GENERATOR_INITIAL_ZERO);

    public FreightVan build(String initialStateName) {
        FreightVan freightVan = new FreightVan(idGenerator.generateNext());

        FreightVanState initialState = new FreightVanStateCreator().create(initialStateName);
        freightVan.setState(initialState);

        return freightVan;
    }
}
