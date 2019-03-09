package com.epam.logistics.base.entitie;

import com.epam.logistics.base.util.generator.exception.IllegalPriorityNameException;
import com.epam.logistics.base.exception.IncorrectThreadClosingException;
import com.epam.logistics.base.state.freightvan.FreightVanState;

public class FreightVan implements Runnable {
    private final int id;

    private FreightVanState state;

    public FreightVan(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

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
