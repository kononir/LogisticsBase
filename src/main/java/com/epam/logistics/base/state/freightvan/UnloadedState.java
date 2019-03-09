package com.epam.logistics.base.state.freightvan;

import com.epam.logistics.base.entitie.FreightVan;
import com.epam.logistics.base.entitie.LogisticsBase;
import com.epam.logistics.base.exception.IncorrectThreadClosingException;
import com.epam.logistics.base.util.generator.exception.IllegalPriorityNameException;
import com.epam.logistics.base.util.generator.impl.PriorityName;
import com.epam.logistics.base.util.writer.MessageWriter;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class UnloadedState extends FreightVanState {
    public UnloadedState(FreightVan freightVan) {
        super(freightVan);
    }

    @Override
    public void queryTerminal() throws IllegalPriorityNameException, IncorrectThreadClosingException {
        MessageWriter messageWriter = MessageWriter.getInstance();
        messageWriter.write("Unloaded freight van #" + getFreightVan().getId() + " query terminal.");

        LogisticsBase logisticsBase = LogisticsBase.getInstance();
        Semaphore freeTerminals = logisticsBase.getFreeTerminals();

        if (!freeTerminals.tryAcquire()) {
            messageWriter.write("Unloaded freight van #" + getFreightVan().getId() + " get in queue.");

            try {
                getInQueueByPriority(PriorityName.NORMAL);
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
            messageWriter.write("Unloaded freight van #" + getFreightVan().getId() + " loads.");

            TimeUnit seconds = TimeUnit.SECONDS;
            seconds.sleep(5);

            FreightVan freightVan = getFreightVan();

            freightVan.setState(new LoadedState(freightVan));
        } catch (InterruptedException e) {
            LogisticsBase logisticsBase = LogisticsBase.getInstance();
            Semaphore freeTerminals = logisticsBase.getFreeTerminals();
            freeTerminals.release();

            throw new IncorrectThreadClosingException();
        }
    }

    @Override
    public void leaveTerminal() {
        MessageWriter messageWriter = MessageWriter.getInstance();
        messageWriter.write("Unloaded freight van #" + getFreightVan().getId() + " leave terminal.");

        LogisticsBase logisticsBase = LogisticsBase.getInstance();
        Semaphore freeTerminals = logisticsBase.getFreeTerminals();
        freeTerminals.release();
    }
}
