package com.epam.logistics.base;

import com.epam.logistics.base.data.parser.BaseParser;
import com.epam.logistics.base.data.parser.exception.ParsingProblemsException;
import com.epam.logistics.base.data.parser.impl.SAXBaseParser;
import com.epam.logistics.base.data.validator.DataValidator;
import com.epam.logistics.base.data.validator.exception.InvalidSchemaPathException;
import com.epam.logistics.base.data.validator.exception.ReadingProblemsException;
import com.epam.logistics.base.data.validator.impl.DataValidatorImpl;
import com.epam.logistics.base.entitie.FreightVan;
import com.epam.logistics.base.entitie.LogisticsBase;
import com.epam.logistics.base.exception.InvalidFileException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WorkStarter {

    public static final Logger LOGGER = LogManager.getRootLogger();

    public static void main(String[] args) {
        System.out.println("Please input path of initialization file: ");

        Scanner scanner = new Scanner(System.in);

        String path = scanner.nextLine();

        try {
            DataValidator validator = new DataValidatorImpl();

            if (!validator.validate(path)) {
                throw new InvalidFileException("Current file with logistic base parameters is invalid!");
            }

            BaseParser baseParser = new SAXBaseParser();
            List<FreightVan> freightVans = baseParser.parse(path);

            startWorking(freightVans);
        } catch (ReadingProblemsException | InvalidFileException | ParsingProblemsException e) {
            LOGGER.error(e);
        } catch (InvalidSchemaPathException e) {
            LOGGER.fatal(e);
        }
    }

    private static void startWorking(List<FreightVan> freightVans) {
        ExecutorService executorService = Executors.newFixedThreadPool(freightVans.size() + 1);

        executorService.execute(LogisticsBase.getInstance());

        for (FreightVan freightVan : freightVans) {
            executorService.execute(freightVan);
        }
    }
}
