package com.epam.logistics.base.data.parser;

import com.epam.logistics.base.data.parser.BaseParser;
import com.epam.logistics.base.data.parser.exception.ParsingProblemsException;
import com.epam.logistics.base.data.parser.exception.ReadingProblemsException;
import com.epam.logistics.base.data.parser.impl.SAXBaseParser;
import com.epam.logistics.base.entitie.van.FreightVan;
import com.epam.logistics.base.state.van.FreightVanState;
import com.epam.logistics.base.state.van.LoadedState;
import com.epam.logistics.base.state.van.LoadedWithPerishableGoodsState;
import com.epam.logistics.base.state.van.UnloadedState;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class SAXBaseParserTests {
    private static final String PATH_WITH_THREE = "src/test/resources/three_freight_vans.xml";
    private static final String INVALID_PATH = "invalid";
    private static final String INCORRECT_XML = "src/test/resources/incorrect.xml";

    private static final int THREE_ELEMENTS = 3;

    private static final int FIRST = 0;
    private static final int SECOND = 1;
    private static final int THIRD = 2;

    private static final FreightVanState LOADED_STATE = new LoadedState();
    private static final FreightVanState UNLOADED_STATE = new UnloadedState();
    private static final FreightVanState LOADED_WITH_PERISHABLE_GOODS_STATE = new LoadedWithPerishableGoodsState();

    @Test
    public void testParseShouldReturnListWithThreeFreightVanWhenFileHasInformationAboutThreeFreightVans()
            throws ParsingProblemsException, ReadingProblemsException {
        BaseParser baseParser = new SAXBaseParser();

        List<FreightVan> freightVans = baseParser.parse(PATH_WITH_THREE);

        Assert.assertEquals(THREE_ELEMENTS, freightVans.size());

        FreightVan firstFreightVan = freightVans.get(FIRST);
        Assert.assertEquals(FIRST, firstFreightVan.getId());
        Assert.assertEquals(LOADED_STATE, firstFreightVan.getState());

        FreightVan secondFreightVan = freightVans.get(SECOND);
        Assert.assertEquals(SECOND, secondFreightVan.getId());
        Assert.assertEquals(UNLOADED_STATE, secondFreightVan.getState());

        FreightVan thirdFreightVan = freightVans.get(THIRD);
        Assert.assertEquals(THIRD, thirdFreightVan.getId());
        Assert.assertEquals(LOADED_WITH_PERISHABLE_GOODS_STATE, thirdFreightVan.getState());
    }

    @Test(expected = ReadingProblemsException.class)
    public void testParseShouldThrowReadingProblemsExceptionWhenPathIsInvalid()
            throws ParsingProblemsException, ReadingProblemsException {
        BaseParser baseParser = new SAXBaseParser();

        baseParser.parse(INVALID_PATH);

        Assert.fail();
    }

    @Test(expected = ParsingProblemsException.class)
    public void testParsingShouldThrowParsingProblemsExceptionWhenXmlIsIncorrect()
            throws ParsingProblemsException, ReadingProblemsException {
        BaseParser baseParser = new SAXBaseParser();

        baseParser.parse(INCORRECT_XML);

        Assert.fail();
    }
}
