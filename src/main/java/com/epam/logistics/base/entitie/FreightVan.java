package com.epam.logistics.base.entitie;

import com.epam.logistics.base.util.generator.exception.IllegalPriorityNameException;
import com.epam.logistics.base.exception.IncorrectThreadClosingException;
import com.epam.logistics.base.state.freightvan.FreightVanState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FreightVan implements Runnable {
    private final int id;

    private FreightVanState state;

    private static final Logger ERROR_LOGGER = LogManager.getRootLogger();

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
        } catch (IncorrectThreadClosingException | IllegalPriorityNameException e) {
            ERROR_LOGGER.error(e);
        }
    }
}
