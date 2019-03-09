package com.epam.logistics.base.state.freightvan;

import com.epam.logistics.base.entitie.FreightVan;
import com.epam.logistics.base.entitie.LogisticsBase;
import com.epam.logistics.base.exception.IncorrectThreadClosingException;
import com.epam.logistics.base.util.generator.exception.IllegalPriorityNameException;
import com.epam.logistics.base.util.generator.impl.PriorityName;
import com.epam.logistics.base.util.writer.MessageWriter;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class LoadedWithPerishableGoodsState extends LoadedState {
    public LoadedWithPerishableGoodsState(FreightVan freightVan) {
        super(freightVan);
    }

    @Override
    public void queryTerminal() throws IllegalPriorityNameException, IncorrectThreadClosingException {
        MessageWriter messageWriter = MessageWriter.getInstance();
        messageWriter.write("Loaded with perishable goods freight van #" + getFreightVan().getId() + " query terminal.");

        LogisticsBase logisticsBase = LogisticsBase.getInstance();
        Semaphore freeTerminals = logisticsBase.getFreeTerminals();

        if (!freeTerminals.tryAcquire()) {
            messageWriter.write("Loaded with perishable goods freight van #" + getFreightVan().getId() + " get in queue.");

            try {
                getInQueueByPriority(PriorityName.PERISHABLE_GOODS);
            } catch (InterruptedException e) {
                freeTerminals.release();

                throw new IncorrectThreadClosingException();
            }
        }
    }

    @Override
    public void workAtTerminal() throws IncorrectThreadClosingException {
        try {
            MessageWriter messageWriter = MessageWriter.getInstance();
            messageWriter.write("Loaded with perishable goods freight van #" + getFreightVan().getId() + " unloads.");

            TimeUnit seconds = TimeUnit.SECONDS;
            seconds.sleep(5);

            FreightVan freightVan = getFreightVan();

            freightVan.setState(new UnloadedState(freightVan));
        } catch (InterruptedException e) {
            LogisticsBase logisticsBase = LogisticsBase.getInstance();
            Semaphore freeTerminals = logisticsBase.getFreeTerminals();
            freeTerminals.release();

            throw new IncorrectThreadClosingException();
        }
    }
}
