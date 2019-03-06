package com.epam.logistics.base.data.parser;

import com.epam.logistics.base.entitie.LogisticsBase;

public interface DataParser {
    LogisticsBase parse(String path);
}
