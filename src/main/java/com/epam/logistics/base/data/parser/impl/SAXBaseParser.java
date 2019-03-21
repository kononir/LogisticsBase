package com.epam.logistics.base.data.parser.impl;

import com.epam.logistics.base.data.parser.BaseParser;
import com.epam.logistics.base.data.parser.exception.ParsingProblemsException;
import com.epam.logistics.base.data.parser.exception.ReadingProblemsException;
import com.epam.logistics.base.entitie.van.FreightVan;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.util.List;

public class SAXBaseParser implements BaseParser {

    @Override
    public List<FreightVan> parse(String path) throws ReadingProblemsException, ParsingProblemsException {
        List<FreightVan> freightVans;

        try {
            XMLReader xmlReader = XMLReaderFactory.createXMLReader();

            SAXParserHandler saxParserHandler = new SAXParserHandler();
            xmlReader.setContentHandler(saxParserHandler);

            xmlReader.parse(path);

            freightVans = saxParserHandler.getFreightVans();
        } catch (SAXException e) {
            throw new ParsingProblemsException("File parsing problems!", e);
        } catch (IOException e) {
            throw new ReadingProblemsException("File reading problems!", e);
        }

        return freightVans;
    }
}
