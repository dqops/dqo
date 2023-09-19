/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dqops.core.jobqueue;

import com.dqops.core.jobqueue.exceptions.DqoQueueJobCancelledException;
import com.dqops.utils.exceptions.DqoRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashSet;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * Special object passed to all methods called during a job execution. This token works as a connection to the job,
 * to detect if the job was cancelled and to register hooks called when the cancellation is requested.
 */
@Slf4j
public class JobCancellationToken {
    private final DqoQueueJob<?> job;
    private final LinkedHashSet<JobCancellationListenerHandle> cancellationListeners = new LinkedHashSet<>();
    private final Object lock = new Object();

    /**
     * Crates a job cancellation token, given a job that could be cancelled.
     * @param job Job instance.
     */
    public JobCancellationToken(DqoQueueJob<?> job) {
        this.job = job;
    }

    /**
     * Creates a dummy cancellation token, not associated to a job.
     * @return Dummy cancellation token.
     */
    public static DummyJobCancellationToken createDummyJobCancellationToken() {
        return new DummyJobCancellationToken();
    }

    /**
     * Cancels the job. Also notifies all cancellation listeners that the job was cancelled.
     */
    public void cancel() {
        if (this.job.getCompletionStatus() != null) {
            return; // the job has completed, too late to cancel it
        }

        try {
            CompletableFuture<?> finishedFuture = this.job.getFinishedFuture();
            if (finishedFuture != null) {
                finishedFuture.cancel(true);
            }
        }
        catch (Throwable ex) {
            throw new DqoRuntimeException("Failed to cancel a job " + this.job.getClass().getSimpleName() + ", error: " + ex.getMessage(), ex);
        }
        this.notifyCancellationListeners();
    }

    /**
     * Returns the job instance.
     * @return Job instance.
     */
    public DqoQueueJob<?> getJob() {
        return job;
    }

    /**
     * Registers a job cancellation callback that will be called when the job is cancelled.
     * @param onJobCancelledCallback Callback to be called when the job is cancelled.
     * @return Listener registration handle that should be closed (or auto-closed, using a try-with-resources pattern) when the handle should be unregistered.
     */
    public JobCancellationListenerHandle registerCancellationListener(Consumer<JobCancellationToken> onJobCancelledCallback) {
        JobCancellationListenerHandle handle = new JobCancellationListenerHandle(this, onJobCancelledCallback);

        synchronized (this.lock) {
            this.cancellationListeners.add(handle);
        }

        return handle;
    }

    /**
     * Unregisters a job cancellation callback (identified by a handle), removing it from the list of cancellation callbacks.
     * @param jobCancellationListenerHandle Job cancellation callback handle to unregister.
     */
    public void unregisterListener(JobCancellationListenerHandle jobCancellationListenerHandle) {
        synchronized (this.lock) {
            this.cancellationListeners.remove(jobCancellationListenerHandle);
        }
    }

    /**
     * Notifies all cancellation listeners that the job was cancelled.
     */
    public void notifyCancellationListeners() {
        JobCancellationListenerHandle[] jobCancellationListenerHandles;

        synchronized (this.lock) {
            jobCancellationListenerHandles = this.cancellationListeners.toArray(JobCancellationListenerHandle[]::new);
        }

        for (JobCancellationListenerHandle cancellationHandle : jobCancellationListenerHandles) {
            try {
                cancellationHandle.getOnJobCancelledCallback().accept(this);
            }
            catch (Exception ex) {
                log.error("Job cancellation listener failed to receive a cancellation notification, error message: " + ex.getMessage(), ex);
            }
        }
    }


    /**
     * Checks if the job was cancelled. This method should be used for a silent (no exception) method of detecting if the job should continue execution or should finish prematurely, because it was cancelled.
     * @return True when the job was cancelled and should stop executing, false when it is not yet cancelled.
     */
    public boolean isCancelled() {
        return this.job.getFinishedFuture().isCancelled();
    }

    /**
     * Checks if the job was cancelled.
     * When the job is not cancelled, the method returns silently.
     * When the job was cancelled, it will throw a runtime exception {@link DqoQueueJobCancelledException}.
     */
    public void throwIfCancelled() {
        if (this.job.getFinishedFuture().isCancelled()) {
            throw new DqoQueueJobCancelledException(this.job.getJobId());
        }
    }
}
