package com.epam.logistics.base.state.freightvan;

import com.epam.logistics.base.entitie.FreightVan;
import com.epam.logistics.base.util.generator.exception.IllegalPriorityNameException;
import com.epam.logistics.base.util.generator.impl.PriorityGenerator;
import com.epam.logistics.base.util.generator.impl.PriorityName;
import com.epam.logistics.base.util.queue.SynchronizedPriorityQueue;
import com.epam.logistics.base.entitie.LogisticsBase;
import com.epam.logistics.base.util.queue.QueueElement;
import com.epam.logistics.base.util.queue.exception.IncorrectThreadClosingException;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class LoadedState extends FreightVanState {
    public LoadedState(FreightVan freightVan) {
        super(freightVan);
    }

    @Override
    public void queryTerminal() throws IncorrectThreadClosingException, IllegalPriorityNameException {
        LogisticsBase logisticsBase = LogisticsBase.getInstance();
        Semaphore freeTerminals = logisticsBase.getFreeTerminals();

        if (!freeTerminals.tryAcquire()) {
            SynchronizedPriorityQueue<FreightVan> synchronizedPriorityQueue = logisticsBase.getSynchronizedPriorityQueue();
            PriorityGenerator generator = synchronizedPriorityQueue.getPriorityGenerator();

            int priorityNumber = generator.generateNext(PriorityName.NORMAL);

            FreightVan freightVan = getFreightVan();
            QueueElement<FreightVan> queueElement = new QueueElement<>(priorityNumber, freightVan);

            synchronizedPriorityQueue.add(queueElement);

            try {
                CountDownLatch queueTurn = queueElement.getQueueTurn();
                queueTurn.await();
            } catch (InterruptedException e) {
                freeTerminals.release();

                throw new IncorrectThreadClosingException();
            }
        }
    }

    @Override
    public void workAtTerminal() throws IncorrectThreadClosingException {
        try {
            TimeUnit seconds = TimeUnit.SECONDS;
            seconds.sleep(60);

            FreightVan freightVan = getFreightVan();

            freightVan.setState(new UnloadedState(freightVan));
        } catch (InterruptedException e) {
            LogisticsBase logisticsBase = LogisticsBase.getInstance();
            Semaphore freeTerminals = logisticsBase.getFreeTerminals();
            freeTerminals.release();

            throw new IncorrectThreadClosingException();
        }
    }

    @Override
    public void leaveTerminal() {
        LogisticsBase logisticsBase = LogisticsBase.getInstance();
        Semaphore freeTerminals = logisticsBase.getFreeTerminals();
        freeTerminals.release();
    }
}
