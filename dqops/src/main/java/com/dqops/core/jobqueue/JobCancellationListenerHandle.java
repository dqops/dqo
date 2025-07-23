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

import java.util.function.Consumer;

/**
 * Handle returned after registering a job cancellation listener on a job. The handle could be closed, which unregisters the listener.
 */
public class JobCancellationListenerHandle implements AutoCloseable {
    private final JobCancellationToken jobCancellationToken;
    private final Consumer<JobCancellationToken> onJobCancelledCallback;

    /**
     * Creates a job cancellation listener handle.
     * @param jobCancellationToken Job cancellation token where the handle will be registered.
     * @param onJobCancelledCallback Cancellation callback.
     */
    public JobCancellationListenerHandle(JobCancellationToken jobCancellationToken,
                                         Consumer<JobCancellationToken> onJobCancelledCallback) {
        assert jobCancellationToken != null;
        assert onJobCancelledCallback != null;

        this.jobCancellationToken = jobCancellationToken;
        this.onJobCancelledCallback = onJobCancelledCallback;
    }

    /**
     * Returns the job cancellation token on which this cancellation listener handle is registered.
     * @return Parent job cancellation token.
     */
    public JobCancellationToken getJobCancellationToken() {
        return jobCancellationToken;
    }

    /**
     * Returns the callback that should be called when the job is cancelled.
     * @return Job cancellation listener that should be called.
     */
    public Consumer<JobCancellationToken> getOnJobCancelledCallback() {
        return onJobCancelledCallback;
    }

    /**
     * Closes this resource, relinquishing any underlying resources.
     * This method is invoked automatically on objects managed by the
     * {@code try}-with-resources statement.
     */
    @Override
    public void close() {
        this.jobCancellationToken.unregisterListener(this);
    }
}
