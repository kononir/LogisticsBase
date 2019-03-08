package com.epam.logistics.base.entitie;

import com.epam.logistics.base.util.generator.exception.IllegalPriorityNameException;
import com.epam.logistics.base.util.queue.exception.IncorrectThreadClosingException;
import com.epam.logistics.base.state.freightvan.FreightVanState;

public class FreightVan implements Runnable {
    private FreightVanState state;

    private LogisticsBase logisticsBase = LogisticsBase.getInstance();

    public void setState(FreightVanState state) {
        this.state = state;
    }

    public void run() {
        try {
            state.queryTerminal();
            state.workAtTerminal();
            state.leaveTerminal();
        } catch (IncorrectThreadClosingException e) {
            e.printStackTrace();
        } catch (IllegalPriorityNameException e) {
            e.printStackTrace();
        }
    }
}
