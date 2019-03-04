package com.epam.logistics.base.entitie;

import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FreightVanQueue {
    private Queue<FreightVan> queue = new PriorityQueue<>();
    private int currentTopPriority;

    private static final int TIME = 10;

    private Lock workingPermission = new ReentrantLock();

    public void add(FreightVan freightVan) {
        workingPermission.lock();

        queue.add(freightVan);

        workingPermission.unlock();
    }

    public Optional<FreightVan> poll() {
        Optional<FreightVan> nextFreightVan;

        try {
            workingPermission.tryLock(TIME, TimeUnit.MILLISECONDS);

            nextFreightVan = Optional.ofNullable(queue.poll());

            workingPermission.unlock();
        } catch (InterruptedException e) {
            nextFreightVan = Optional.empty();
        }

        return nextFreightVan;
    }
}
