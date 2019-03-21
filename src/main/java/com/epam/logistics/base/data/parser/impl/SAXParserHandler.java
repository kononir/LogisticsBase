package com.epam.logistics.base.data.parser.impl;

import com.epam.logistics.base.entitie.van.FreightVan;
import com.epam.logistics.base.entitie.base.LogisticsBaseBuilder;
import com.epam.logistics.base.entitie.van.FreightVanBuilder;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class SAXParserHandler extends DefaultHandler {
    private List<FreightVan> freightVans = new ArrayList<>();

    private int numberWithPerishableGoods;
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
                    numberWithPerishableGoods++;
                }

                FreightVan freightVan = new FreightVanBuilder().build(initialStateName);
                freightVans.add(freightVan);

                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if ("base".equals(localName)) {
            new LogisticsBaseBuilder().build(terminalsNumber, numberWithPerishableGoods);
        }
    }
}
