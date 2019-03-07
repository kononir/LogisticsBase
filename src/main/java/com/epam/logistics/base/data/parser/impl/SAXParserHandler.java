package com.epam.logistics.base.data.parser.impl;

import com.epam.logistics.base.entitie.FreightVan;
import com.epam.logistics.base.queue.FreightVanQueue;
import com.epam.logistics.base.entitie.LogisticsBase;
import com.epam.logistics.base.state.freightvan.FreightVanState;
import com.epam.logistics.base.state.freightvan.FreightVanStateCreator;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class SAXParserHandler extends DefaultHandler {

    private List<FreightVan> freightVans;
    private LogisticsBase logisticsBase = LogisticsBase.getInstance();

    public List<FreightVan> getFreightVans() {
        return freightVans;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (localName) {
            case "base":
                String terminalsStr = attributes.getValue("terminals-number");

                createLogisticsBase(Integer.parseInt(terminalsStr));

                break;
            case "freight-vans":
                freightVans = new ArrayList<>();

                break;
            case "freight-van":
                String initialStateName = attributes.getValue("state");

                createFreightVan(initialStateName);

                break;
        }
    }

    private void createLogisticsBase(int terminalsNumber) {
        Semaphore freeTerminals = new Semaphore(terminalsNumber);

        logisticsBase.setFreeTerminals(freeTerminals);

        FreightVanQueue freightVanQueue = new FreightVanQueue();
        logisticsBase.setFreightVanQueue(freightVanQueue);
    }

    private void createFreightVan(String initialStateName) {
        FreightVan freightVan = new FreightVan();

        FreightVanState initialState = new FreightVanStateCreator(freightVan).create(initialStateName);
        freightVan.setState(initialState);

        freightVans.add(freightVan);
    }
}
