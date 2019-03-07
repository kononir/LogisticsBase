package com.epam.logistics.base.data.parser;

import com.epam.logistics.base.data.parser.exception.ParsingProblemsException;
import com.epam.logistics.base.data.validator.exception.ReadingProblemsException;
import com.epam.logistics.base.entitie.FreightVan;

import java.util.List;

public interface BaseParser {
    List<FreightVan> parse(String path) throws ReadingProblemsException, ParsingProblemsException;
}
