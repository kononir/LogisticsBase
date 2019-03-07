package com.epam.logistics.base.state.freightvan;

import com.epam.logistics.base.entitie.FreightVan;
import com.epam.logistics.base.queue.FreightVanQueue;
import com.epam.logistics.base.entitie.LogisticsBase;

import java.util.concurrent.Semaphore;

public class LoadedState extends FreightVanState {
    public LoadedState(FreightVan freightVan) {
        super(freightVan);
    }

    @Override
    public void queryTerminal() {
        LogisticsBase logisticsBase = LogisticsBase.getInstance();

        FreightVanQueue freightVanQueue = logisticsBase.getFreightVanQueue();

        Semaphore freeTerminals = logisticsBase.getFreeTerminals();

        if (freeTerminals.availablePermits() == 0) {
            freightVanQueue.add(getFreightVan());
        }

        try {
            freeTerminals.acquire();
        } catch (InterruptedException e) {
            freeTerminals.release();
        }
    }

    @Override
    public void workAtTerminal() {

    }

    @Override
    public void leaveTerminal() {

    }
}
