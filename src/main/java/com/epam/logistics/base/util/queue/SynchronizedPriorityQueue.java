package com.epam.logistics.base.util.queue;

import com.epam.logistics.base.util.generator.Generator;
import com.epam.logistics.base.util.generator.impl.PriorityGenerator;

import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SynchronizedPriorityQueue<T> {
    private Queue<QueueElement<T>> queue = new PriorityQueue<>();

    private PriorityGenerator priorityGenerator;

    private ReadWriteLock workingPermission = new ReentrantReadWriteLock();

    private static final int TIME = 10;

    public SynchronizedPriorityQueue(PriorityGenerator priorityGenerator) {
        this.priorityGenerator = priorityGenerator;
    }

    public PriorityGenerator getPriorityGenerator() {
        return priorityGenerator;
    }

    public void add(QueueElement<T> element) {
        Lock writePermission = workingPermission.writeLock();

        writePermission.lock();

        try {
            queue.add(element);
        } finally {
            writePermission.unlock();
        }
    }

    public Optional<QueueElement<T>> poll() {
        Optional<QueueElement<T>> nextFreightVan;

        Lock writePermission = workingPermission.writeLock();

        try {
            writePermission.tryLock(TIME, TimeUnit.MILLISECONDS);

            try {
                nextFreightVan = Optional.ofNullable(queue.poll());
            } finally {
                writePermission.unlock();
            }

        } catch (InterruptedException e) {
            nextFreightVan = Optional.empty();
        }

        return nextFreightVan;
    }

    public int getSize() {
        int queueSize;

        Lock readPermission = workingPermission.readLock();

        try {
            readPermission.lock();

            queueSize = queue.size();
        } finally {
            readPermission.unlock();
        }

        return queueSize;
    }
}
