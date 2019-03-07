package com.epam.logistics.base.entitie;

import com.epam.logistics.base.state.freightvan.FreightVanState;

public class FreightVan implements Runnable, Comparable<FreightVan> {
    private int priorityId;

    private FreightVanState state;

    private LogisticsBase logisticsBase = LogisticsBase.getInstance();

    public void setState(FreightVanState state) {
        this.state = state;
    }

    public void run() {
        state.queryTerminal();

        state.workAtTerminal();

        state.leaveTerminal();
    }

    public int compareTo(FreightVan o) {
        return priorityId - o.priorityId;
    }
}
