package com.epam.logistics.base.data.parser;

import com.epam.logistics.base.data.parser.exception.ParsingProblemsException;
import com.epam.logistics.base.data.parser.exception.ReadingProblemsException;
import com.epam.logistics.base.entitie.van.FreightVan;

import java.util.List;

public interface BaseParser {
    List<FreightVan> parse(String path) throws ReadingProblemsException, ParsingProblemsException;
}
