package com.epam.logistics.base.entitie.base;

import com.epam.logistics.base.entitie.van.FreightVan;
import com.epam.logistics.base.util.generator.Generator;
import com.epam.logistics.base.util.generator.impl.IncrementalGenerator;
import com.epam.logistics.base.util.generator.impl.IncrementalPriorityGenerator;
import com.epam.logistics.base.util.queue.SynchronizedPriorityQueue;

import java.util.Arrays;
import java.util.concurrent.Semaphore;

public class LogisticsBaseBuilder {
    private static final int GENERATOR_INITIAL_ZERO = 0;

    public void build(int terminalsNumber, int numberWithPerishableGoods) {
        LogisticsBase logisticsBase = LogisticsBase.getInstance();

        Semaphore freeTerminals = new Semaphore(terminalsNumber);
        logisticsBase.setFreeTerminals(freeTerminals);

        Generator<Integer> lessPriorityGenerator = new IncrementalGenerator(numberWithPerishableGoods);
        Generator<Integer> morePriorityGenerator = new IncrementalGenerator(GENERATOR_INITIAL_ZERO);

        IncrementalPriorityGenerator incrementalPriorityGenerator
                = new IncrementalPriorityGenerator(Arrays.asList(lessPriorityGenerator, morePriorityGenerator));

        SynchronizedPriorityQueue<FreightVan> synchronizedPriorityQueue
                = new SynchronizedPriorityQueue<>(incrementalPriorityGenerator);

        logisticsBase.setSynchronizedPriorityQueue(synchronizedPriorityQueue);
    }
}
