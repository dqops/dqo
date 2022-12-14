/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.core.jobqueue;

import ai.dqo.core.jobqueue.monitoring.DqoJobEntryParametersModel;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Base class for DQO jobs.
 */
public abstract class DqoQueueJob<T> {
    private CompletableFuture<T> future = new CompletableFuture<T>();

    /**
     * Executes the job inside the job queue.
     * @param jobExecutionContext Job execution context.
     */
    public final void execute(DqoJobExecutionContext jobExecutionContext) {
        try {
            T result = this.onExecute(jobExecutionContext);
            this.future.complete(result);
        }
        catch (Exception ex) {
            this.future.completeExceptionally(ex);
            throw new DqoQueueJobExecutionException(ex);
        }
    }

    /**
     * Job internal implementation method that should be implemented by derived jobs.
     * @param jobExecutionContext Job execution context.
     * @return Optional result value that could be returned by the job.
     */
    public abstract T onExecute(DqoJobExecutionContext jobExecutionContext);

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
     * Returns a concurrency constraint that will limit the number of parallel running jobs.
     * Return null when the job has no concurrency limits (an unlimited number of jobs can run at the same time).
     * @return Optional concurrency constraint that limits the number of parallel jobs or null, when no limits are required.
     */
    public abstract JobConcurrencyConstraint getConcurrencyConstraint();

    /**
     * Returns the job completable future used to await for the job to finish.
     * @return Completable future.
     */
    public CompletableFuture<T> getFuture() {
        return future;
    }

    /**
     * Waits for the result of the job.
     * @return Result of the job.
     */
    public T getResult() {
        try {
            return this.future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new DqoQueueJobExecutionException(e);
        }
    }

    /**
     * Waits until the job finishes. Halts the execution of the current thread.
     */
    public void waitForFinish() {
        getResult();
    }
}
