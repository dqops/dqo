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

import com.dqops.utils.exceptions.DqoRuntimeException;

import java.util.concurrent.ExecutionException;

/**
 * Exception thrown when it was not possible to retrieve a result from a job that was pushed to a job queue.
 * It could be caused by an {@link InterruptedException} or an {@link java.util.concurrent.ExecutionException}.
 */
public class DqoQueueJobExecutionException extends DqoRuntimeException {
    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public DqoQueueJobExecutionException(String message) {
        super(message);
    }

    public DqoQueueJobExecutionException(Throwable cause) {
        super(cause);
    }

    /**
     * Extracts the real exception that was raised during a job execution.
     * @return Real exception
     */
    public Throwable getRealCause() {
        if (this.getCause() instanceof ExecutionException) {
            ExecutionException executionException = (ExecutionException) this.getCause();
            return executionException.getCause();
        } else {
            return this.getCause();
        }
    }
}
