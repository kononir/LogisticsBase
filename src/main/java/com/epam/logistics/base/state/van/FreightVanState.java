package com.epam.logistics.base.state.van;

import com.epam.logistics.base.entitie.van.FreightVan;
import com.epam.logistics.base.entitie.base.LogisticsBase;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        return (this.getClass() == obj.getClass());
    }
}
