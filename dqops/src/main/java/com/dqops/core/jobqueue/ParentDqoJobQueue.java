/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.jobqueue;

import com.dqops.core.principal.DqoUserPrincipal;

/**
 * DQOps job queue for parent jobs - manages a pool of threads that are executing operations.
 * This version of the job queue is a separate job queue that only accepts parent jobs.
 */
public interface ParentDqoJobQueue {
    /**
     * Starts the job queue, creates a thread pool.
     */
    void start();

    /**
     * Stops the job queue.
     */
    void stop();

    /**
     * Checks if the job queue is started and is running. The health check depends on a working job queue.
     * @return True when the job queue is started and is running, false when the job queue was not yet started or is stopping.
     */
    boolean isStarted();

    /**
     * Pushes a parent job to the job queue without waiting.
     *
     * @param job Job to be pushed. Must be a {@link ParentDqoQueueJob}
     * @param principal Principal that will be used to run the job.
     * @return Started job summary and a future to await for finish.
     */
    <T> PushJobResult<T> pushJob(ParentDqoQueueJob<T> job, DqoUserPrincipal principal);

    /**
     * Cancels a job given a job id.
     * @param jobId Job id.
     */
    void cancelJob(DqoQueueJobId jobId);
}
