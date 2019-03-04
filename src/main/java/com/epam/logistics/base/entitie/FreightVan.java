package com.epam.logistics.base.entitie;

public class FreightVan implements Runnable, Comparable<FreightVan> {
    private int priorityId;

    public FreightVan(int priorityId) {
        this.priorityId = priorityId;
    }

    public void run() {

    }

    public int compareTo(FreightVan o) {
        return priorityId - o.priorityId;
    }
}
