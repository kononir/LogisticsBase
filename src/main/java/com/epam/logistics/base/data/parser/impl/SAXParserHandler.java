package com.epam.logistics.base.data.parser.impl;

import com.epam.logistics.base.entitie.FreightVan;
import com.epam.logistics.base.util.generator.Generator;
import com.epam.logistics.base.util.generator.impl.IncrementalGenerator;
import com.epam.logistics.base.util.generator.impl.IncrementalPriorityGenerator;
import com.epam.logistics.base.util.queue.SynchronizedPriorityQueue;
import com.epam.logistics.base.entitie.LogisticsBase;
import com.epam.logistics.base.state.freightvan.FreightVanState;
import com.epam.logistics.base.state.freightvan.FreightVanStateCreator;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Semaphore;

public class SAXParserHandler extends DefaultHandler {
    private static final int GENERATOR_INITIAL_ZERO = 0;

    private Generator<Integer> idGenerator = new IncrementalGenerator(GENERATOR_INITIAL_ZERO);
    private List<FreightVan> freightVans = new ArrayList<>();

    private int normalPriorityGeneratorInitial;
    private int terminalsNumber;

    public List<FreightVan> getFreightVans() {
        return freightVans;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (localName) {
            case "base":
                String terminalsStr = attributes.getValue("terminals-number");
                terminalsNumber = Integer.parseInt(terminalsStr);

                break;
            case "freight-van":
                String initialStateName = attributes.getValue("state");

                if ("loaded_with_perishable_goods".equals(initialStateName)) {
                    normalPriorityGeneratorInitial++;
                }

                createFreightVan(initialStateName);

                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if ("base".equals(localName)) {
            createLogisticsBase();
        }
    }

    private void createLogisticsBase() {
        LogisticsBase logisticsBase = LogisticsBase.getInstance();

        Semaphore freeTerminals = new Semaphore(terminalsNumber);
        logisticsBase.setFreeTerminals(freeTerminals);

        Generator<Integer> lessPriorityGenerator = new IncrementalGenerator(normalPriorityGeneratorInitial);
        Generator<Integer> morePriorityGenerator = new IncrementalGenerator(GENERATOR_INITIAL_ZERO);

        IncrementalPriorityGenerator incrementalPriorityGenerator
                = new IncrementalPriorityGenerator(Arrays.asList(lessPriorityGenerator, morePriorityGenerator));

        SynchronizedPriorityQueue<FreightVan> synchronizedPriorityQueue
                = new SynchronizedPriorityQueue<>(incrementalPriorityGenerator);

        logisticsBase.setSynchronizedPriorityQueue(synchronizedPriorityQueue);
    }

    private void createFreightVan(String initialStateName) {
        FreightVan freightVan = new FreightVan(idGenerator.generateNext());

        FreightVanState initialState = new FreightVanStateCreator(freightVan).create(initialStateName);
        freightVan.setState(initialState);

        freightVans.add(freightVan);
    }
}
