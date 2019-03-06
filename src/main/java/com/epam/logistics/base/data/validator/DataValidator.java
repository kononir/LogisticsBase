package com.epam.logistics.base.data.validator;

import com.epam.logistics.base.data.validator.exception.InvalidSchemaPathException;
import com.epam.logistics.base.data.validator.exception.ReadingProblemsException;

public interface DataValidator {
    boolean validate(String path) throws ReadingProblemsException, InvalidSchemaPathException;
}
