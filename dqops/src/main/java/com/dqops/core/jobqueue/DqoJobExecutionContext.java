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

/**
 * Context object passed to a job when it is executed. Provides access to the job id.
 */
public class DqoJobExecutionContext {
    private DqoQueueJobId jobId;
    private JobCancellationToken cancellationToken;

    /**
     * Creates a job execution context.
     * @param jobId Job id.
     * @param cancellationToken Job cancellation token. The job implementation should frequently verify
     *                          if a job was cancelled by checking the cancellation status of this cancellation token.
     */
    public DqoJobExecutionContext(DqoQueueJobId jobId, JobCancellationToken cancellationToken) {
        this.jobId = jobId;
        this.cancellationToken = cancellationToken;
    }

    /**
     * Returns the job id. The job id also contains job ids of its parent jobs.
     * @return Job id.
     */
    public DqoQueueJobId getJobId() {
        return jobId;
    }

    /**
     * Returns a reference to a job cancellation token. The job cancellation token supports checking whether the job was cancelled by the user, so the
     * job's logic should stop. The job cancellation token also supports registering cancellation listeners when an asynchronous
     * or a blocking operation should be cancelled from a separate thread.
     * @return Job cancellation token.
     */
    public JobCancellationToken getCancellationToken() {
        return cancellationToken;
    }
}
