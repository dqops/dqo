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

import com.dqops.core.jobqueue.DqoJobQueueEntry;

/**
 * DQOps job queue concurrency limiter. Maintains a count of concurrent jobs running in parallel in order to enforce
 * concurrency limits.
 */
public interface DqoJobConcurrencyLimiter {
    /**
     * Registers a new job that has a concurrency constraint. The concurrency limit for that job is checked to decide
     * if the job is allowed to run. If the concurrency limit is not reached, the running jobs count is increased by one
     * and the job is returned, so it is allowed to run.
     * If the concurrency limit is exceeded, the job is parked on a queue and the method returns null, so the job should not be started.
     *
     * @param dqoJobQueueEntry
     * @return The job that was passed (<code>dqoJobQueueEntry</code>) when the job is allowed to run, null when the job was parked.
     */
    DqoJobQueueEntry parkOrRegisterStartedJob(DqoJobQueueEntry dqoJobQueueEntry);

    /**
     * Tries to take (and dequeue) an available job that is below the concurrency limit.
     *
     * @return A dequeued job that should be started or null. The running jobs count is increased for the returned job, because it is assumed that it will be started instantly.
     */
    DqoJobQueueEntry takeFirstAvailable();

    /**
     * Retrieves an array of parked jobs.
     *
     * @return Array of parked jobs, that are awaiting because their concurrency limits are exceeded.
     */
    DqoJobQueueEntry[] getParkedJobs();

    /**
     * Notifies the concurrency limiter that a concurrency limited job has finished and the running job count for that type of jobs could be decreased.
     * @param dqoJobQueueEntry Job entry that was just finished.
     */
    void notifyJobFinished(DqoJobQueueEntry dqoJobQueueEntry);

    /**
     * Clears (removes) all jobs and limits.
     */
    void clear();
}
