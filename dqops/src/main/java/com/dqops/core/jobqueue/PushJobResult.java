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

import java.util.concurrent.CompletableFuture;

/**
 * Result object returned for a new job that is started. Returns a job ID and a completable future to wait for the job to finish.
 * @param <T> Job result type.
 */
public class PushJobResult<T> {
    private CompletableFuture<T> finishedFuture;
    private DqoQueueJobId jobId;

    /**
     * Creates a result object for an operation that started a new job.
     * @param finishedFuture Future to await for the result.
     * @param jobId Job id.
     */
    public PushJobResult(CompletableFuture<T> finishedFuture, DqoQueueJobId jobId) {
        this.finishedFuture = finishedFuture;
        this.jobId = jobId;
    }

    /**
     * Future to await for the job to finish. Provides access to the result of a job if a job produces any result.
     * @return Future to await for the job to finish.
     */
    public CompletableFuture<T> getFinishedFuture() {
        return finishedFuture;
    }

    /**
     * Returns the job id that was assigned to the job.
     * @return Job id of a new job.
     */
    public DqoQueueJobId getJobId() {
        return jobId;
    }
}
