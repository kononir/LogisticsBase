package com.epam.logistics.base.state.freightvan;

import com.epam.logistics.base.entitie.FreightVan;
import com.epam.logistics.base.util.generator.exception.IllegalPriorityNameException;
import com.epam.logistics.base.util.queue.exception.IncorrectThreadClosingException;

public abstract class FreightVanState {
    private FreightVan freightVan;

    public FreightVanState(FreightVan freightVan) {
        this.freightVan = freightVan;
    }

    public FreightVan getFreightVan() {
        return freightVan;
    }

    public abstract void queryTerminal() throws IncorrectThreadClosingException, IllegalPriorityNameException;

    public abstract void workAtTerminal() throws IncorrectThreadClosingException;

    public abstract void leaveTerminal();
}
