package com.epam.logistics.base.state;

import com.epam.logistics.base.entitie.FreightVan;
import com.epam.logistics.base.entitie.Terminal;

public abstract class FreightVanState {
    private FreightVan freightVan;

    public FreightVanState(FreightVan freightVan) {
        this.freightVan = freightVan;
    }

    public abstract Terminal queryTerminal();

    public abstract void workAtTerminal(Terminal terminal);

    public abstract void leaveTerminal(Terminal terminal);
}
