package com.epam.logistics.base.data.validator.impl;

import com.epam.logistics.base.data.validator.DataValidator;
import com.epam.logistics.base.data.validator.exception.InvalidSchemaPathException;
import com.epam.logistics.base.data.validator.exception.ReadingProblemsException;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.net.URL;

public class DataValidatorImpl implements DataValidator {

    private static final String SCHEMA_PATH = "logistics_base_schema.xsd";
    private static final String SCHEMA_LANGUAGE = XMLConstants.W3C_XML_SCHEMA_NS_URI;

    @Override
    public boolean validate(String path) throws InvalidSchemaPathException, ReadingProblemsException {
        boolean result;

        try {
            ClassLoader classLoader = getClass().getClassLoader();
            URL schemaPathUrl = classLoader.getResource(SCHEMA_PATH);

            if (schemaPathUrl == null) {
                throw new InvalidSchemaPathException("There is no such schema!");
            }

            SchemaFactory schemaFactory = SchemaFactory.newInstance(SCHEMA_LANGUAGE);
            Schema schema = schemaFactory.newSchema(schemaPathUrl);

            Validator validator = schema.newValidator();
            Source source = new StreamSource(path);
            validator.validate(source);

            result = true;
        } catch (SAXException e) {
            result = false;
        } catch (IOException e) {
            throw new ReadingProblemsException("File reading problem!", e);
        }

        return result;
    }
}
