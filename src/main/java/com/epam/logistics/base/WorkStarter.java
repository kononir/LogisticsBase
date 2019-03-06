package com.epam.logistics.base;

import com.epam.logistics.base.data.validator.DataValidator;
import com.epam.logistics.base.data.validator.exception.InvalidSchemaPathException;
import com.epam.logistics.base.data.validator.exception.ReadingProblemsException;
import com.epam.logistics.base.data.validator.impl.DataValidatorImpl;
import com.epam.logistics.base.exception.InvalidFileException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

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
        } catch (ReadingProblemsException | InvalidFileException e) {
            LOGGER.error(e);
        } catch (InvalidSchemaPathException e) {
            LOGGER.fatal(e);
        }
    }
}
