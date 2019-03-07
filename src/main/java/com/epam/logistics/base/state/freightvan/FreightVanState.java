package com.epam.logistics.base.state.freightvan;

import com.epam.logistics.base.entitie.FreightVan;

public abstract class FreightVanState {
    private FreightVan freightVan;

    public FreightVanState(FreightVan freightVan) {
        this.freightVan = freightVan;
    }

    public FreightVan getFreightVan() {
        return freightVan;
    }

    public abstract void queryTerminal();

    public abstract void workAtTerminal();

    public abstract void leaveTerminal();
}
