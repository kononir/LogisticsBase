package com.epam.logistics.base.util.queue;

import com.epam.logistics.base.util.generator.PriorityGenerator;

import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SynchronizedPriorityQueue<T> {
    private Queue<QueueElement<T>> queue = new PriorityQueue<>();

    private PriorityGenerator<Integer> incrementalPriorityGenerator;

    private ReadWriteLock workingPermission = new ReentrantReadWriteLock();

    public SynchronizedPriorityQueue(PriorityGenerator<Integer> incrementalPriorityGenerator) {
        this.incrementalPriorityGenerator = incrementalPriorityGenerator;
    }

    public PriorityGenerator<Integer> getIncrementalPriorityGenerator() {
        return incrementalPriorityGenerator;
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
            writePermission.lock();

            nextFreightVan = Optional.ofNullable(queue.poll());
        } finally {
            writePermission.unlock();
        }

        return nextFreightVan;
    }
}
