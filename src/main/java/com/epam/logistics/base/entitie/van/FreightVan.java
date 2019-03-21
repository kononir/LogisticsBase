package com.epam.logistics.base.entitie.van;

import com.epam.logistics.base.util.generator.exception.IllegalPriorityNameException;
import com.epam.logistics.base.exception.IncorrectThreadClosingException;
import com.epam.logistics.base.state.van.FreightVanState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

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

    public FreightVanState getState() {
        return state;
    }

    public void run() {
        try {
            state.queryTerminal(this);
            state.workAtTerminal(this);
            state.leaveTerminal(this);
        } catch (IncorrectThreadClosingException | IllegalPriorityNameException e) {
            ERROR_LOGGER.error(e);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        FreightVan that = (FreightVan) obj;
        FreightVanState thatState = that.state;

        boolean idEquals = (id == that.id);
        boolean statesEquals = state.equals(thatState);

        return (idEquals && statesEquals);
    }

    @Override
    public int hashCode() {
        return (31 * id);
    }
}
