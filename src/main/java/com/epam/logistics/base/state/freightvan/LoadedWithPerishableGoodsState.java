package com.epam.logistics.base.state.freightvan;

import com.epam.logistics.base.entitie.FreightVan;
import com.epam.logistics.base.entitie.LogisticsBase;
import com.epam.logistics.base.exception.IncorrectThreadClosingException;
import com.epam.logistics.base.util.generator.exception.IllegalPriorityNameException;
import com.epam.logistics.base.util.generator.impl.Priority;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoadedWithPerishableGoodsState extends FreightVanState {
    private static final Logger BASE_LOGGER = LogManager.getLogger("BaseLogger");

    @Override
    public void queryTerminal(FreightVan freightVan)
            throws IllegalPriorityNameException, IncorrectThreadClosingException {
        BASE_LOGGER.info("Loaded with perishable goods freight van #" + freightVan.getId() + " get in queue.");

        LogisticsBase logisticsBase = LogisticsBase.getInstance();
        logisticsBase.getInQueueByPriority(Priority.PERISHABLE_GOODS, freightVan);
    }

    @Override
    public void workAtTerminal(FreightVan freightVan) throws IncorrectThreadClosingException {
        BASE_LOGGER.info("Loaded with perishable goods freight van #" + freightVan.getId() + " unloads.");

        super.workAtTerminal(freightVan);
    }

    @Override
    public void leaveTerminal(FreightVan freightVan) {
        BASE_LOGGER.info("Loaded with perishable goods freight van #" + freightVan.getId() + " leave terminal.");

        super.leaveTerminal(freightVan);
    }
}
