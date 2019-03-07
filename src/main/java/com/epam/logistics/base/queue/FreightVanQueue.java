package com.epam.logistics.base.queue;

import com.epam.logistics.base.entitie.FreightVan;

import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FreightVanQueue {
    private Queue<FreightVan> queue = new PriorityQueue<>();

    private ReadWriteLock workingPermission = new ReentrantReadWriteLock();

    private static final int TIME = 10;

    public void add(FreightVan freightVan) {
        Lock writePermission = workingPermission.writeLock();

        writePermission.lock();

        try {
            queue.add(freightVan);
        } finally {
            writePermission.unlock();
        }
    }

    public Optional<FreightVan> poll() {
        Optional<FreightVan> nextFreightVan;

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

    public int getFreightVansNumber() {
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
