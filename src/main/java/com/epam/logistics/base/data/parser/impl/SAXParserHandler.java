package com.epam.logistics.base.data.parser.impl;

import com.epam.logistics.base.entitie.FreightVan;
import com.epam.logistics.base.util.generator.Generator;
import com.epam.logistics.base.util.generator.impl.IncrementalGenerator;
import com.epam.logistics.base.util.generator.impl.PriorityGenerator;
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

    private List<FreightVan> freightVans;

    private static final int morePriorityGeneratorInitial = 0;
    private int lessPriorityGeneratorInitial;
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
            case "freight-vans":
                freightVans = new ArrayList<>();

                break;
            case "freight-van":
                String initialStateName = attributes.getValue("state");

                if ("loaded_with_perishable_goods".equals(initialStateName)) {
                    lessPriorityGeneratorInitial++;
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

        Generator<Integer> lessPriorityGenerator = new IncrementalGenerator(lessPriorityGeneratorInitial);
        Generator<Integer> morePriorityGenerator = new IncrementalGenerator(morePriorityGeneratorInitial);

        PriorityGenerator priorityGenerator
                = new PriorityGenerator(Arrays.asList(lessPriorityGenerator, morePriorityGenerator));

        SynchronizedPriorityQueue<FreightVan> synchronizedPriorityQueue
                = new SynchronizedPriorityQueue<>(priorityGenerator);

        logisticsBase.setSynchronizedPriorityQueue(synchronizedPriorityQueue);
    }

    private void createFreightVan(String initialStateName) {
        FreightVan freightVan = new FreightVan();

        FreightVanState initialState = new FreightVanStateCreator(freightVan).create(initialStateName);
        freightVan.setState(initialState);

        freightVans.add(freightVan);
    }
}
