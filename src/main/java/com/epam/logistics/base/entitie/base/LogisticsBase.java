package com.epam.logistics.base.entitie.base;

import com.epam.logistics.base.entitie.van.FreightVan;
import com.epam.logistics.base.exception.IncorrectThreadClosingException;
import com.epam.logistics.base.util.generator.PriorityGenerator;
import com.epam.logistics.base.util.generator.exception.IllegalPriorityNameException;
import com.epam.logistics.base.util.generator.impl.Priority;
import com.epam.logistics.base.util.queue.QueueElement;
import com.epam.logistics.base.util.queue.SynchronizedPriorityQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    private static final Logger ERROR_LOGGER = LogManager.getRootLogger();

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

    public void setSynchronizedPriorityQueue(SynchronizedPriorityQueue<FreightVan> synchronizedPriorityQueue) {
        this.synchronizedPriorityQueue = synchronizedPriorityQueue;
    }

    public void getInQueueByPriority(Priority priority, FreightVan freightVan)
            throws IllegalPriorityNameException, IncorrectThreadClosingException {
        try {
            PriorityGenerator<Integer> generator = synchronizedPriorityQueue.getIncrementalPriorityGenerator();
            int priorityNumber = generator.generateNext(priority);
            QueueElement<FreightVan> queueElement = new QueueElement<>(priorityNumber, freightVan);

            synchronizedPriorityQueue.add(queueElement);

            queueElement.awaitQueueTurn();
        } catch (InterruptedException e) {
            throw new IncorrectThreadClosingException(e);
        }
    }

    public void setFreeTerminals(Semaphore freeTerminals) {
        this.freeTerminals = freeTerminals;
    }

    public void releaseTerminal() {
        freeTerminals.release();
    }

    @Override
    public void run() {
        try {
            Optional<QueueElement<FreightVan>> optional;

            waitForVans();

            while ((optional = synchronizedPriorityQueue.poll()).isPresent()) {
                freeTerminals.acquire();

                QueueElement<FreightVan> queueElement = optional.get();

                CountDownLatch queueTurn = queueElement.getQueueTurn();
                queueTurn.countDown();
            }
        } catch (InterruptedException e) {
            ERROR_LOGGER.error(e);
        }
    }

    public void waitForVans() throws InterruptedException {
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        timeUnit.sleep(5);
    }
}
