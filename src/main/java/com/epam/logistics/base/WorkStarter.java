package com.epam.logistics.base;

import com.epam.logistics.base.data.BaseParser;
import com.epam.logistics.base.data.exception.ParsingProblemsException;
import com.epam.logistics.base.data.exception.ReadingProblemsException;
import com.epam.logistics.base.data.impl.SAXBaseParser;
import com.epam.logistics.base.entitie.FreightVan;
import com.epam.logistics.base.entitie.LogisticsBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WorkStarter {
    public static final String PATH = "src/main/resources/base.xml";

    public static final Logger LOGGER = LogManager.getRootLogger();

    public static void main(String[] args) {
        try {
            BaseParser baseParser = new SAXBaseParser();
            List<FreightVan> freightVans = baseParser.parse(PATH);

            ExecutorService executorService = Executors.newFixedThreadPool(freightVans.size() + 1);

            for (FreightVan freightVan : freightVans) {
                executorService.execute(freightVan);
            }

            executorService.execute(LogisticsBase.getInstance());

            executorService.shutdown();
        } catch (ParsingProblemsException e) {
            LOGGER.error(e);
        } catch (ReadingProblemsException e) {
            LOGGER.fatal(e);
        }
    }
}
