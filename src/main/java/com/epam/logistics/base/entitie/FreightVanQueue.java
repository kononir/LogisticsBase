package com.epam.logistics.base.entitie;

import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FreightVanQueue {
    private Queue<FreightVan> queue = new PriorityQueue<>();

    private Lock workingPermission = new ReentrantLock();

    private static final int TIME = 10;

    public void add(FreightVan freightVan) {
        workingPermission.lock();

        try {
            queue.add(freightVan);
        } finally {
            workingPermission.unlock();
        }
    }

    public Optional<FreightVan> poll() {
        Optional<FreightVan> nextFreightVan;

        try {
            workingPermission.tryLock(TIME, TimeUnit.MILLISECONDS);

            try {
                nextFreightVan = Optional.ofNullable(queue.poll());
            } finally {
                workingPermission.unlock();
            }

        } catch (InterruptedException e) {
            nextFreightVan = Optional.empty();
        }

        return nextFreightVan;
    }
}
