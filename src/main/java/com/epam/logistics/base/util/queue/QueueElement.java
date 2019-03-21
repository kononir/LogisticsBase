package com.epam.logistics.base.util.queue;

import java.util.concurrent.CountDownLatch;

public class QueueElement<T> implements Comparable<QueueElement> {
    private int priorityId;

    private T element;

    private static final int DOWN_COUNT = 1;
    private final CountDownLatch queueTurn = new CountDownLatch(DOWN_COUNT);

    public QueueElement(int priorityId, T element) {
        this.priorityId = priorityId;
        this.element = element;
    }

    public CountDownLatch getQueueTurn() {
        return queueTurn;
    }

    public int compareTo(QueueElement o) {
        return priorityId - o.priorityId;
    }

    public void awaitQueueTurn() throws InterruptedException {
        queueTurn.await();
    }
}
