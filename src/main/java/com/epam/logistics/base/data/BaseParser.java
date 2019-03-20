package com.epam.logistics.base.data;

import com.epam.logistics.base.data.exception.ParsingProblemsException;
import com.epam.logistics.base.data.exception.ReadingProblemsException;
import com.epam.logistics.base.entitie.FreightVan;

import java.util.List;

public interface BaseParser {
    List<FreightVan> parse(String path) throws ReadingProblemsException, ParsingProblemsException;
}
