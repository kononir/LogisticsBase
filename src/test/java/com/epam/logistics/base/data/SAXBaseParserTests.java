package com.epam.logistics.base.data;

import com.epam.logistics.base.data.exception.ParsingProblemsException;
import com.epam.logistics.base.data.exception.ReadingProblemsException;
import com.epam.logistics.base.data.impl.SAXBaseParser;
import com.epam.logistics.base.entitie.van.FreightVan;

import java.util.List;

public class SAXBaseParserTests {
    private static final String PATH_WITH_THREE = "src/test/resources/three_freight_vans.xml";



    public void testParseShouldReturnListWithThreeFreightVanWhenFileHasInformationAboutThreeFreightVans()
            throws ParsingProblemsException, ReadingProblemsException {
        BaseParser baseParser = new SAXBaseParser();

        List<FreightVan> freightVans = baseParser.parse(PATH_WITH_THREE);


    }
}
