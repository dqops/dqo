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

import com.dqops.core.jobqueue.concurrency.JobConcurrencyConstraint;
import com.dqops.core.jobqueue.exceptions.DqoQueueJobCancelledException;
import com.dqops.core.jobqueue.exceptions.DqoQueueJobExecutionException;
import com.dqops.core.jobqueue.monitoring.DqoJobEntryParametersModel;
import com.dqops.core.principal.DqoUserPrincipal;

import java.util.concurrent.*;
import java.util.function.BiConsumer;

/**
 * Base class for DQOps jobs.
 */
public abstract class DqoQueueJob<T> {
    private final CompletableFuture<T> finishedFuture = new CompletableFuture<T>();
    private final CompletableFuture<DqoQueueJob> startedFuture = new CompletableFuture<>();
    private LinkedBlockingQueue<BiConsumer<DqoQueueJob<?>, DqoJobCompletionStatus>> onFinishedCallbacks = new LinkedBlockingQueue<>();
    private final JobCancellationToken cancellationToken;
    private final Object lock = new Object();
    private DqoQueueJobId jobId;
    private String jobBusinessKey;
    private Throwable jobExecutionException;
    private DqoUserPrincipal principal;

    /**
     * Creates and configures a new job.
     */
    public DqoQueueJob() {
        this.cancellationToken = new JobCancellationToken(this);
        this.cancellationToken.registerCancellationListener(cancellationToken -> {
            LinkedBlockingQueue<BiConsumer<DqoQueueJob<?>, DqoJobCompletionStatus>> capturedOnFinishedCallbacks = null;
            synchronized (this.lock) {
                if (this.startedFuture.isDone()) {
                    this.finishedFuture.cancel(true);
                    return; // notifications to the job finished listeners will be done by the thread that is running the job
                }

                // the job has not yet started, we can run notifications
                this.finishedFuture.cancel(true);
                capturedOnFinishedCallbacks = this.onFinishedCallbacks;
                this.onFinishedCallbacks = new LinkedBlockingQueue<>();
            }

            try {
                for (BiConsumer<DqoQueueJob<?>, DqoJobCompletionStatus> finishedCallback = null;
                     (finishedCallback = capturedOnFinishedCallbacks.poll(0L, TimeUnit.SECONDS)) != null; ) {
                    finishedCallback.accept(this, DqoJobCompletionStatus.CANCELLED);
                }
            } catch (InterruptedException e) {
                throw new DqoQueueJobExecutionException(e);
            }
        });
    }

    /**
     * Checks if the job has finished and returns the completion status.
     * @return Job completion status (if the job has finished or was cancelled) or null, when the job was not yet finished (or cancelled).
     */
    public DqoJobCompletionStatus getCompletionStatus() {
        if (!this.finishedFuture.isDone()) {
            return null;
        }

        if (this.finishedFuture.isCompletedExceptionally()) {
            return DqoJobCompletionStatus.FAILED;
        }

        if (this.finishedFuture.isCancelled()) {
            return DqoJobCompletionStatus.CANCELLED;
        }

        return DqoJobCompletionStatus.FINISHED;
    }

    /**
     * Registers an on finish callback. The callback will be called when the job has finished.
     * This method also supports a corner case when the callback is registered on an already finished job. In that case, the callback is called instantly.
     * @param onFinishedCallback Callback that will be called when the job finishes.
     */
    public void registerOnFinishedCallback(BiConsumer<DqoQueueJob<?>, DqoJobCompletionStatus> onFinishedCallback) {
        try {
            synchronized (this.lock) {
                this.onFinishedCallbacks.put(onFinishedCallback);
            }

            if (this.finishedFuture.isDone()) {
                BiConsumer<DqoQueueJob<?>, DqoJobCompletionStatus> abandonedCallback = this.onFinishedCallbacks.poll(0L, TimeUnit.SECONDS);
                if (abandonedCallback != null) {
                    DqoJobCompletionStatus completionStatus = this.getCompletionStatus();
                    abandonedCallback.accept(this, completionStatus);
                }
            }
        } catch (InterruptedException e) {
            throw new DqoQueueJobExecutionException(e);
        }
    }

    /**
     * Executes the job inside the job queue.
     * @param jobExecutionContext Job execution context.
     */
    public final void execute(DqoJobExecutionContext jobExecutionContext) {
        try {
            synchronized (this.lock) {
                this.startedFuture.complete(this);
            }

            if (this.finishedFuture.isCancelled()) {
                throw new DqoQueueJobCancelledException(this);
            }

            T result = this.onExecute(jobExecutionContext);
            this.finishedFuture.complete(result);
        }
        catch (DqoQueueJobCancelledException cex) {
            assert this.finishedFuture.isCancelled();
            throw cex;
        }
        catch (CancellationException jcex) {
            assert this.finishedFuture.isCancelled();
            throw new DqoQueueJobCancelledException(jobExecutionContext.getJobId());
        }
        catch (Throwable ex) {
            this.jobExecutionException = ex;
            this.finishedFuture.completeExceptionally(ex);
            throw new DqoQueueJobExecutionException(ex);
        }
        finally {
            try {
                for (BiConsumer<DqoQueueJob<?>, DqoJobCompletionStatus> finishedCallback = null;
                     (finishedCallback = this.onFinishedCallbacks.poll(0L, TimeUnit.SECONDS)) != null; ) {
                    finishedCallback.accept(this, getCompletionStatus());
                }
            } catch (InterruptedException e) {
                throw new DqoQueueJobExecutionException(e);
            }
        }
    }

    /**
     * Job internal implementation method that should be implemented by derived jobs.
     * @param jobExecutionContext Job execution context.
     * @return Optional result value that could be returned by the job.
     */
    public abstract T onExecute(DqoJobExecutionContext jobExecutionContext);

    /**
     * Returns the job id that was assigned to the job. The job has a job id when the job was pushed to a job queue, which means it is scheduled for execution.
     * @return Job ID.
     */
    public DqoQueueJobId getJobId() {
        return jobId;
    }

    /**
     * Sets (assigns) a job ID. This method is called by a DQOps job queue when the job is accepted on a queue and queued for execution.
     * @param jobId Job id.
     */
    public void setJobId(DqoQueueJobId jobId) {
        this.jobId = jobId;
    }

    /**
     * Returns a user assigned job business key that is a unique identifier of a job used to find the job.
     * @return Job business key or null.
     */
    public String getJobBusinessKey() {
        return jobBusinessKey;
    }

    /**
     * Sets a job business key that is a user assigned job unique identifier.
     * @param jobBusinessKey Job business key.
     */
    public void setJobBusinessKey(String jobBusinessKey) {
        this.jobBusinessKey = jobBusinessKey;
    }

    /**
     * Returns the user principal associated with the job.
     * @return User principal stored on the job.
     */
    public DqoUserPrincipal getPrincipal() {
        return principal;
    }

    /**
     * Sets a reference to the user principal that will be associated with the job.
     * @param principal User principal to use within the job evaluation.
     */
    public void setPrincipal(DqoUserPrincipal principal) {
        this.principal = principal;
    }

    /**
     * Returns a job type that this job class is running. Used to identify jobs.
     * @return Job type.
     */
    public abstract DqoJobType getJobType();

    /**
     * Creates a typed parameters model that could be sent back to the UI.
     * The parameters model could contain a subset of parameters.
     * @return Job queue parameters that are easy to serialize and shown in the UI.
     */
    public abstract DqoJobEntryParametersModel createParametersModel();

    /**
     * Returns an array of concurrency constraint that will limit the number of parallel running jobs.
     * Return null when the job has no concurrency limits (an unlimited number of jobs can run at the same time).
     * @return Optional concurrency constraint that limits the number of parallel jobs or null, when no limits are required.
     */
    public abstract JobConcurrencyConstraint[] getConcurrencyConstraints();

    /**
     * Returns the job's completable future used to await for the job to finish.
     * @return Completable future that will be completed when the job finishes, returns the result of the job (if a job returns a result).
     */
    public CompletableFuture<T> getFinishedFuture() {
        return finishedFuture;
    }

    /**
     * Returns the job's completable future that will be completed when the job is taken from the job queue and starts execution, but before it is finished.
     * @return Completable future used to wait until a job is started.
     */
    public CompletableFuture<DqoQueueJob> getStartedFuture() {
        return startedFuture;
    }

    /**
     * Returns the job cancellation token.
     * @return Job cancellation token.
     */
    public JobCancellationToken getCancellationToken() {
        return cancellationToken;
    }

    /**
     * Returns the exception that was thrown by the job code when the job failed.
     * @return The exception that was thrown by the job implementation or null when no exception was thrown.
     */
    public Throwable getJobExecutionException() {
        return jobExecutionException;
    }

    /**
     * Waits for the result of the job.
     * @return Result of the job.
     */
    public T getResult() {
        try {
            return this.finishedFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new DqoQueueJobExecutionException(e);
        }
    }

    /**
     * Waits until the job finishes. Halts the execution of the current thread until the job is finished.
     */
    public void waitForFinish() {
        getResult();
    }

    /**
     * Waits for the job to be start execution. Halts the execution of the current thread until the job is started (starts executing).
     */
    public void waitForStarted() {
        try {
            this.getFinishedFuture().get();
        } catch (InterruptedException | ExecutionException e) {
            throw new DqoQueueJobExecutionException(e);
        }
    }
}
