/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.core.locks;

import com.dqops.core.configuration.DqoCoreConfigurationProperties;
import com.dqops.utils.exceptions.DqoRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Counter of active locks, to detect nested locking that can lead to deadlocks.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Slf4j
public final class ThreadLocksCounterImpl implements ThreadLocksCounter {
    private final Object lock = new Object();
    private boolean enabled;
    private final Map<Long, Integer> readLocksPerThread = new LinkedHashMap<>();
    private final Map<Long, Integer> writeLocksPerThread = new LinkedHashMap<>();

    /**
     * Default injection constructor.
     * @param coreConfigurationProperties Configuration object with a flag to enable thread counting and logging.
     */
    @Autowired
    public ThreadLocksCounterImpl(DqoCoreConfigurationProperties coreConfigurationProperties) {
        this.enabled = coreConfigurationProperties.isLogMultipleLocking();
    }

    /**
     * Increments the number of read locks for the current thread.
     */
    @Override
    public void incrementReadLock() {
        if (!this.enabled) {
            return;
        }

        Thread currentThread = Thread.currentThread();
        Long threadId = currentThread.getId();

        synchronized (this.lock) {
            if (this.writeLocksPerThread.containsKey(threadId)) {
                if (log.isWarnEnabled()) {
                    String stackTrace = String.join("\n", Arrays.stream(currentThread.getStackTrace()).map(stackTraceElement -> stackTraceElement.toString()).collect(Collectors.toList()));
                    log.warn("A thread is obtaining a read lock, while already having a write lock, stack trace: " + stackTrace);
                }
            }

            Integer currentReadLocksCount = this.readLocksPerThread.get(threadId);
            if (currentReadLocksCount == null) {
                this.readLocksPerThread.put(threadId, 1);
            } else {
                this.readLocksPerThread.put(threadId, currentReadLocksCount + 1);
                if (log.isWarnEnabled()) {
                    String stackTrace = String.join("\n", Arrays.stream(currentThread.getStackTrace()).map(stackTraceElement -> stackTraceElement.toString()).collect(Collectors.toList()));
                    log.warn("A thread has more than one read locks, stack trace: " + stackTrace);
                }
            }
        }
    }

    /**
     * Decrements the number of read locks for the current thread.
     */
    @Override
    public void decrementReadLock() {
        if (!this.enabled) {
            return;
        }

        Thread currentThread = Thread.currentThread();
        Long threadId = currentThread.getId();

        synchronized (this.lock) {
            Integer currentReadLocksCount = this.readLocksPerThread.get(threadId);
            if (currentReadLocksCount == null) {
                throw new DqoRuntimeException("No read locks were acquired, cannot decrement the lock count.");
            } else {
                if (currentReadLocksCount == 1) {
                    this.readLocksPerThread.remove(threadId);
                } else {
                    this.readLocksPerThread.put(threadId, currentReadLocksCount - 1);
                }
            }
        }
    }

    /**
     * Increments the number of write locks for the current thread.
     */
    @Override
    public void incrementWriteLock() {
        if (!this.enabled) {
            return;
        }

        Thread currentThread = Thread.currentThread();
        Long threadId = currentThread.getId();

        synchronized (this.lock) {
            if (this.readLocksPerThread.containsKey(threadId)) {
                if (log.isWarnEnabled()) {
                    String stackTrace = String.join("\n", Arrays.stream(currentThread.getStackTrace()).map(stackTraceElement -> stackTraceElement.toString()).collect(Collectors.toList()));
                    log.warn("A thread is obtaining a write lock, while already having some read locks, stack trace: " + stackTrace);
                }
            }

            Integer currentReadLocksCount = this.writeLocksPerThread.get(threadId);
            if (currentReadLocksCount == null) {
                this.writeLocksPerThread.put(threadId, 1);
            } else {
                if (log.isWarnEnabled()) {
                    String stackTrace = String.join("\n", Arrays.stream(currentThread.getStackTrace()).map(stackTraceElement -> stackTraceElement.toString()).collect(Collectors.toList()));
                    log.warn("A thread has more than one write locks, stack trace: " + stackTrace);
                }
                this.writeLocksPerThread.put(threadId, currentReadLocksCount + 1);
            }
        }
    }

    /**
     * Decrements the number of write locks for the current thread.
     */
    @Override
    public void decrementWriteLock() {
        if (!this.enabled) {
            return;
        }

        Thread currentThread = Thread.currentThread();
        Long threadId = currentThread.getId();

        synchronized (this.lock) {
            Integer currentReadLocksCount = this.writeLocksPerThread.get(threadId);
            if (currentReadLocksCount == null) {
                throw new DqoRuntimeException("No write locks were acquired, cannot decrement the lock count.");
            } else {
                if (currentReadLocksCount == 1) {
                    this.writeLocksPerThread.remove(threadId);
                } else {
                    this.writeLocksPerThread.put(threadId, currentReadLocksCount - 1);
                }
            }
        }
    }
}
