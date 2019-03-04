package com.epam.logistics.base.state;

import com.epam.logistics.base.entitie.FreightVan;

public abstract class FreightVanState {
    private FreightVan freightVan;

    public FreightVanState(FreightVan freightVan) {
        this.freightVan = freightVan;
    }
}
