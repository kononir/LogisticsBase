package com.epam.logistics.base.util.writer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MessageWriter {
    private static MessageWriter instance = new MessageWriter();

    private Lock writePermission = new ReentrantLock();

    private static final Logger LOGGER = LogManager.getLogger("BaseWriter");

    public static MessageWriter getInstance() {
        return instance;
    }

    public void write(String message) {
        writePermission.lock();

        try {
            LOGGER.info(message);
        } finally {
            writePermission.unlock();
        }
    }
}
