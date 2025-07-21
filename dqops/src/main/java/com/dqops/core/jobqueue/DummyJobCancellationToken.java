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

import com.dqops.core.jobqueue.exceptions.DqoQueueJobCancelledException;

/**
 * Dummy job cancellation token that does nothing and is associated to a job.
 */
public class DummyJobCancellationToken extends JobCancellationToken {
    private boolean isCancelled;

    /**
     * Crates a job cancellation token, given a job that could be cancelled.
     *
     * @param job Job instance.
     */
    public DummyJobCancellationToken(DqoQueueJob<?> job) {
        super(job);
    }

    /**
     * Crates a dummy job cancellation token without a job
     */
    public DummyJobCancellationToken() {
        super(null);
    }

    /**
     * Cancels the job. Also notifies all cancellation listeners that the job was cancelled.
     */
    @Override
    public void cancel() {
        this.isCancelled = true;
    }

    /**
     * Checks if the job was cancelled. This method should be used for a silent (no exception) method of detecting if the job should continue execution or should finish prematurely, because it was cancelled.
     *
     * @return True when the job was cancelled and should stop executing, false when it is not yet cancelled.
     */
    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    /**
     * Checks if the job was cancelled.
     * When the job is not cancelled, the method returns silently.
     * When the job was cancelled, it will throw a runtime exception {@link DqoQueueJobCancelledException}.
     */
    @Override
    public void throwIfCancelled() {
        if (this.isCancelled) {
            throw new DqoQueueJobCancelledException();
        }
    }
}
