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

import java.util.Collection;

/**
 * DQOps job queue - manages a pool of threads that are executing operations.
 * This is the entry point to manage the main DQOps job queue that runs independent and child jobs, but not parent jobs.
 */
public interface DqoJobQueue {
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
     * Pushes a job to the job queue without waiting.
     *
     * @param job Job to be pushed.
     * @param principal Principal that will be used to run the job.
     * @return Started job summary and a future to await for finish.
     */
    <T> PushJobResult<T> pushJob(DqoQueueJob<T> job, DqoUserPrincipal principal);

    /**
     * Pushes a job to the job queue without waiting, storing also a reference to the parent job.
     *
     * @param job Job to be pushed.
     * @param parentJobId Parent job id.
     * @param principal Principal that will be used to run the job.
     * @return Started job summary and a future to await for finish.
     */
    <T> PushJobResult<T> pushJob(DqoQueueJob<T> job, DqoQueueJobId parentJobId, DqoUserPrincipal principal);

    /**
     * Pushes a collection of child jobs.
     * @param childJobs Collection of child jobs.
     * @param parentJobId Parent job id.
     * @param principal Principal that will be used to run the job.
     * @return Child jobs container that will track the completion of all child jobs.
     * @param <T> Child job result type.
     */
    <T> ChildDqoQueueJobsContainer<T> pushChildJobs(Collection<DqoQueueJob<T>> childJobs, DqoQueueJobId parentJobId, DqoUserPrincipal principal);

    /**
     * Cancels a job given a job id.
     * @param jobId Job id.
     */
    void cancelJob(DqoQueueJobId jobId);

    /**
     * Finds the job id of a job given a job business key, a unique user assigned job id.
     * @param jobBusinessKey Job business key.
     * @return Job id object when the job was found or null.
     */
    DqoQueueJobId lookupJobIdByBusinessKey(String jobBusinessKey);
}
