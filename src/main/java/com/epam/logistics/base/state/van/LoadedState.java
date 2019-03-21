package com.epam.logistics.base.state.van;

import com.epam.logistics.base.entitie.van.FreightVan;
import com.epam.logistics.base.entitie.base.LogisticsBase;
import com.epam.logistics.base.exception.IncorrectThreadClosingException;
import com.epam.logistics.base.util.generator.exception.IllegalPriorityNameException;
import com.epam.logistics.base.util.generator.impl.Priority;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoadedState extends FreightVanState {
    private static final Logger BASE_LOGGER = LogManager.getLogger("BaseLogger");

    @Override
    public void queryTerminal(FreightVan freightVan)
            throws IncorrectThreadClosingException, IllegalPriorityNameException {
        BASE_LOGGER.info("Loaded freight van #" + freightVan.getId() + " get in queue.");

        LogisticsBase logisticsBase = LogisticsBase.getInstance();
        logisticsBase.getInQueueByPriority(Priority.NORMAL, freightVan);
    }

    @Override
    public void workAtTerminal(FreightVan freightVan) throws IncorrectThreadClosingException {
        BASE_LOGGER.info("Loaded freight van #" + freightVan.getId() + " unloads.");

        super.workAtTerminal(freightVan);

        freightVan.setState(new UnloadedState());
    }

    @Override
    public void leaveTerminal(FreightVan freightVan) {
        BASE_LOGGER.info("Loaded freight van #" + freightVan.getId() + " leave terminal.");

        super.leaveTerminal(freightVan);
    }
}
