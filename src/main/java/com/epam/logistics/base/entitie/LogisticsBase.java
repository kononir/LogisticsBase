package com.epam.logistics.base.entitie;

import java.util.List;
import java.util.concurrent.Semaphore;

public class LogisticsBase {
    private static LogisticsBase instance = new LogisticsBase();

    private List<Terminal> terminals;
    private FreightVanQueue freightVanQueue;

    public Semaphore freeTerminals;

    private LogisticsBase() {
    }

    public static LogisticsBase getInstance() {
        return instance;
    }

    public void setTerminals(List<Terminal> terminals) {
        this.terminals = terminals;
    }

    public void setFreightVanQueue(FreightVanQueue freightVanQueue) {
        this.freightVanQueue = freightVanQueue;
    }

    public void setFreeTerminals(Semaphore freeTerminals) {
        this.freeTerminals = freeTerminals;
    }
}
