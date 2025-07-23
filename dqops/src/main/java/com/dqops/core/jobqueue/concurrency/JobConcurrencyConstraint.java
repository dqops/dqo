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

/**
 * Configuration of the concurrency limit (degree of parallelism) for each job, configured at a level that has a concurrency limit.
 */
public class JobConcurrencyConstraint {
    private final JobConcurrencyTarget concurrencyTarget;
    private Integer concurrentJobsLimit;

    /**
     * Creates a concurrency constraint.
     * @param concurrencyTarget Concurrency target that identifies the job type and the node on which the limit is appliede.
     * @param concurrentJobsLimit Number of concurrent jobs that could be executed on that node.
     */
    public JobConcurrencyConstraint(JobConcurrencyTarget concurrencyTarget, Integer concurrentJobsLimit) {
        if (concurrentJobsLimit == null || concurrentJobsLimit < 1) {
            throw new IllegalArgumentException("concurrencyJobsLimit cannot be null or below 1.");
        }

        this.concurrencyTarget = concurrencyTarget;
        this.concurrentJobsLimit = concurrentJobsLimit;
    }

    /**
     * Returns the concurrency target.
     * @return Concurrency target.
     */
    public JobConcurrencyTarget getConcurrencyTarget() {
        return concurrencyTarget;
    }

    /**
     * Returns the number of concurrent jobs that can execute on the node.
     * @return Concurrency limit.
     */
    public Integer getConcurrentJobsLimit() {
        return concurrentJobsLimit;
    }
}
