/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.jobqueue.concurrency;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * A group of parked dqo jobs that share the same concurrency limit and are limited within the same concurrency target object.
 */
public class ConcurrentJobLimit {
    /**
     * Placeholder value for an unlimited number of jobs for the concurrency target.
     */
    public static final int UNLIMITED_CONCURRENCY_JOB_LIMIT = Integer.MAX_VALUE;

    private final AtomicInteger atomicLimit = new AtomicInteger(UNLIMITED_CONCURRENCY_JOB_LIMIT);
    private final AtomicInteger atomicRunningJobsCount = new AtomicInteger(0);

    /**
     * Default constructor. Sets an unlimited limit.
     */
    public ConcurrentJobLimit() {
    }

    /**
     * Creates a new concurrency job limit with a given limit.
     * @param limit Initial limit.
     */
    public ConcurrentJobLimit(Integer limit) {
        this.setNewLimit(limit);
    }

    /**
     * Sets a new parallel jobs limit.
     * @param limit New limit. Null value will disable limits.
     * @return Previous limit.
     */
    public int setNewLimit(Integer limit) {
        if (limit == null ) {
            return this.atomicLimit.getAndSet(UNLIMITED_CONCURRENCY_JOB_LIMIT);
        }

        assert limit > 0;

        return this.atomicLimit.getAndSet(limit);
    }

    /**
     * Increments the count of concurrent jobs that are running. This method should be called by a dqo queue execution job
     * when a concurrency limited job was picked up and started.
     * @return Count of jobs running.
     */
    public int incrementRunningJobsCount() {
        return this.atomicRunningJobsCount.incrementAndGet();
    }

    /**
     * Decrements the count of running jobs.
     * @return Number of running jobs after the decrease.
     */
    public int decrementRunningJobsCount() {
        return this.atomicRunningJobsCount.decrementAndGet();
    }

    /**
     * Checks if the number of concurrent jobs reached the limit and no more jobs could be stated.
     * @return True when no more concurrent jobs could be started, false when the limit is not yet reached.
     */
    public boolean isConcurrencyLimitReached() {
        return this.atomicRunningJobsCount.get() >= this.atomicLimit.get();
    }

    /**
     * Returns the current number of running tasks.
     * @return Number of concurrent running tasks.
     */
    public int getRunningJobsCount() {
        return this.atomicRunningJobsCount.get();
    }
}
