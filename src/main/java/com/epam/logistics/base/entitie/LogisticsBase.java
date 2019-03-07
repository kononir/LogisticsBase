package com.epam.logistics.base.entitie;

import com.epam.logistics.base.queue.FreightVanQueue;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LogisticsBase {
    private static LogisticsBase instance = new LogisticsBase();
    private static AtomicBoolean instanceIsCreated = new AtomicBoolean(false);

    private FreightVanQueue freightVanQueue;

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

    public FreightVanQueue getFreightVanQueue() {
        return freightVanQueue;
    }

    public void setFreightVanQueue(FreightVanQueue freightVanQueue) {
        this.freightVanQueue = freightVanQueue;
    }

    public void setFreeTerminals(Semaphore freeTerminals) {
        this.freeTerminals = freeTerminals;
    }
}
