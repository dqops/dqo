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

import com.dqops.core.jobqueue.monitoring.DqoJobStatus;
import com.dqops.utils.exceptions.DqoRuntimeException;
import com.google.api.gax.rpc.InvalidArgumentException;

/**
 * DQOps job completion status, which says how a job has finished: the job was finished, the job failed with an exception, the job was cancelled.
 */
public enum DqoJobCompletionStatus {
    /**
     * The job has finishes successfully.
     */
    FINISHED,

    /**
     * The job failed with an exception.
     */
    FAILED,

    /**
     * The job was cancelled.
     */
    CANCELLED;

    /**
     * Converts the job status calculated from the completable future to a compatible job status.
     * Not all statuses are covered.
     * @return Job status.
     */
    public DqoJobStatus toJobStatus() {
        switch (this) {
            case FINISHED:
                return DqoJobStatus.finished;
            case FAILED:
                return DqoJobStatus.failed;
            case CANCELLED:
                return DqoJobStatus.cancelled;
            default:
                throw new DqoRuntimeException("Unknown status " + this);
        }
    }
}
