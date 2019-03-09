package com.epam.logistics.base.entitie;

import com.epam.logistics.base.util.queue.SynchronizedPriorityQueue;
import com.epam.logistics.base.util.queue.QueueElement;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LogisticsBase implements Runnable {
    private static LogisticsBase instance = new LogisticsBase();
    private static AtomicBoolean instanceIsCreated = new AtomicBoolean(false);

    private SynchronizedPriorityQueue<FreightVan> synchronizedPriorityQueue;

    private Semaphore freeTerminals;

    private LogisticsBase() {
    }

    public static LogisticsBase getInstance() {
        LogisticsBase localInstance = instance;

        if (instanceIsCreated.get()) {

            Lock lock = new ReentrantLock();

            try {
                lock.lock();

                localInstance = instance;

                if (instanceIsCreated.getAndSet(true)) {
                    instance = localInstance = new LogisticsBase();
                }
            } finally {
                lock.unlock();
            }
        }

        return localInstance;
    }

    public Semaphore getFreeTerminals() {
        return freeTerminals;
    }

    public SynchronizedPriorityQueue<FreightVan> getSynchronizedPriorityQueue() {
        return synchronizedPriorityQueue;
    }

    public void setSynchronizedPriorityQueue(SynchronizedPriorityQueue<FreightVan> synchronizedPriorityQueue) {
        this.synchronizedPriorityQueue = synchronizedPriorityQueue;
    }

    public void setFreeTerminals(Semaphore freeTerminals) {
        this.freeTerminals = freeTerminals;
    }

    @Override
    public void run() {
        try {
            Optional<QueueElement<FreightVan>> optional;

            waitFreightVans(1);

            while ((optional = synchronizedPriorityQueue.poll()).isPresent()) {
                freeTerminals.acquire();

                QueueElement queueElement = optional.get();

                CountDownLatch queueTurn = queueElement.getQueueTurn();
                queueTurn.countDown();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void waitFreightVans(int seconds) throws InterruptedException {
        TimeUnit secondsUnit = TimeUnit.SECONDS;
        secondsUnit.sleep(seconds);
    }
}
