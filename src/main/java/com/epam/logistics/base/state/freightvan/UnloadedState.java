package com.epam.logistics.base.state.freightvan;

import com.epam.logistics.base.entitie.FreightVan;

public class UnloadedState extends FreightVanState {
    public UnloadedState(FreightVan freightVan) {
        super(freightVan);
    }

    @Override
    public void queryTerminal() {
    }

    @Override
    public void workAtTerminal() {

    }

    @Override
    public void leaveTerminal() {

    }
}
