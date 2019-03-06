package com.epam.logistics.base.entitie;

import com.epam.logistics.base.state.FreightVanState;

public class FreightVan implements Runnable, Comparable<FreightVan> {
    private int priorityId;
    private FreightVanState state;

    private LogisticsBase logisticsBase;

    public FreightVan(int priorityId, LogisticsBase logisticsBase) {
        this.priorityId = priorityId;
        this.logisticsBase = logisticsBase;
    }

    public void setState(FreightVanState state) {
        this.state = state;
    }

    public void run() {
        Terminal terminal = state.queryTerminal();

        state.workAtTerminal(terminal);

        state.leaveTerminal(terminal);
    }

    public int compareTo(FreightVan o) {
        return priorityId - o.priorityId;
    }
}
