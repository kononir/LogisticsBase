package com.epam.logistics.base.state.freightvan;

import com.epam.logistics.base.entitie.FreightVan;
import com.epam.logistics.base.entitie.LogisticsBase;
import com.epam.logistics.base.util.generator.PriorityGenerator;
import com.epam.logistics.base.util.generator.exception.IllegalPriorityNameException;
import com.epam.logistics.base.exception.IncorrectThreadClosingException;
import com.epam.logistics.base.util.generator.impl.PriorityName;
import com.epam.logistics.base.util.queue.QueueElement;
import com.epam.logistics.base.util.queue.SynchronizedPriorityQueue;

import java.util.concurrent.CountDownLatch;

public abstract class FreightVanState {
    private FreightVan freightVan;

    public FreightVanState(FreightVan freightVan) {
        this.freightVan = freightVan;
    }

    public FreightVan getFreightVan() {
        return freightVan;
    }

    public abstract void queryTerminal() throws IncorrectThreadClosingException, IllegalPriorityNameException;

    public abstract void workAtTerminal() throws IncorrectThreadClosingException;

    public abstract void leaveTerminal();

    protected void getInQueueByPriority(PriorityName priorityName)
            throws IllegalPriorityNameException, InterruptedException {
        LogisticsBase logisticsBase = LogisticsBase.getInstance();

        SynchronizedPriorityQueue<FreightVan> synchronizedPriorityQueue = logisticsBase.getSynchronizedPriorityQueue();
        PriorityGenerator<Integer> generator = synchronizedPriorityQueue.getIncrementalPriorityGenerator();

        int priorityNumber = generator.generateNext(priorityName);

        FreightVan freightVan = getFreightVan();
        QueueElement<FreightVan> queueElement = new QueueElement<>(priorityNumber, freightVan);

        synchronizedPriorityQueue.add(queueElement);

        CountDownLatch queueTurn = queueElement.getQueueTurn();
        queueTurn.await();
    }
}
