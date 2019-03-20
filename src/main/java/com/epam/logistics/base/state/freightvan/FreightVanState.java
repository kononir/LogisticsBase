package com.epam.logistics.base.state.freightvan;

import com.epam.logistics.base.entitie.FreightVan;
import com.epam.logistics.base.entitie.LogisticsBase;
import com.epam.logistics.base.exception.IncorrectThreadClosingException;
import com.epam.logistics.base.util.generator.exception.IllegalPriorityNameException;

import java.util.concurrent.TimeUnit;

public abstract class FreightVanState {

    public abstract void queryTerminal(FreightVan freightVan)
            throws IncorrectThreadClosingException, IllegalPriorityNameException;

    public void workAtTerminal(FreightVan freightVan)
            throws IncorrectThreadClosingException {
        try {
            TimeUnit seconds = TimeUnit.SECONDS;
            seconds.sleep(5);

            freightVan.setState(new UnloadedState());
        } catch (InterruptedException e) {
            LogisticsBase logisticsBase = LogisticsBase.getInstance();
            logisticsBase.releaseTerminal();

            throw new IncorrectThreadClosingException();
        }
    }

    public void leaveTerminal(FreightVan freightVan) {
        LogisticsBase logisticsBase = LogisticsBase.getInstance();
        logisticsBase.releaseTerminal();
    }
}
