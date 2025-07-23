/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.core.jobqueue.exceptions;

import com.dqops.core.jobqueue.DqoQueueJob;
import com.dqops.core.jobqueue.DqoQueueJobId;
import com.dqops.utils.exceptions.DqoRuntimeException;

/**
 * Exception thrown when a job was cancelled. It is a runtime exception.
 */
public class DqoQueueJobCancelledException extends DqoRuntimeException {
    private DqoQueueJob<?> job;
    private DqoQueueJobId jobId;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public DqoQueueJobCancelledException() {
    }

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.
     */
    public DqoQueueJobCancelledException(DqoQueueJob<?> job) {
        this.job = job;
    }

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.
     */
    public DqoQueueJobCancelledException(DqoQueueJobId jobId) {
        this.jobId = jobId;
    }

    /**
     * Returns the job that was cancelled.
     * @return Cancelled job.
     */
    public DqoQueueJob<?> getJob() {
        return job;
    }

    /**
     * Returns the cancelled job id.
     * @return Job id.
     */
    public DqoQueueJobId getJobId() {
        return jobId;
    }
}
