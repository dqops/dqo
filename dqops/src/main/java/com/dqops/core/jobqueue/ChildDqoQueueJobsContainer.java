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
import com.dqops.core.jobqueue.exceptions.DqoQueueJobExecutionException;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * Container of child DQOps job queue jobs that should be started together.
 * Tracks the progress of running these jobs.
 */
public class ChildDqoQueueJobsContainer<T> {
    private LinkedHashSet<DqoQueueJob<T>> childJobsSet;
    private LinkedList<DqoQueueJob<T>> remainingJobs;
    private AtomicInteger finishedJobsCount = new AtomicInteger(0);
    private CompletableFuture<List<T>> allJobsFinished = new CompletableFuture<>();
    private final Object lock = new Object();

    /**
     * Creates a new container of child jobs.
     * @param childJobs Collection of child jobs.
     */
    public ChildDqoQueueJobsContainer(Collection<DqoQueueJob<T>> childJobs) {
        this.childJobsSet = new LinkedHashSet<>(childJobs);
        this.remainingJobs = new LinkedList<>(this.childJobsSet);
        if (this.childJobsSet.isEmpty()) {
            this.allJobsFinished.complete(new ArrayList<>());
        }
    }

    /**
     * Returns a collection of all jobs.
     * @return Collection of all jobs.
     */
    public Collection<DqoQueueJob<T>> getJobs() {
        return Collections.unmodifiableCollection(this.childJobsSet);
    }

    /**
     * Callback method that should be called from a callback attached to a child job.
     * Notifies the child job container that a child job has finished. It is used to decrement the number of concurrent jobs.
     * Returns the next job that should be scheduled (When there are still unscheduled child jobs) or null when there are no more child jobs.
     * @param job Job that has finished.
     * @param completionStatus Completion status.
     * @return Next child job to execute or null when all jobs were executed.
     */
    public DqoQueueJob<T> notifyChildJobFinished(DqoQueueJob<T> job, DqoJobCompletionStatus completionStatus) {
        synchronized (this.lock) {
            int newTotalFinishedJobsCount = this.finishedJobsCount.incrementAndGet();

            if (newTotalFinishedJobsCount != this.childJobsSet.size()) {
                return this.remainingJobs.poll();
            }
        }

        // the last job has finished, finishing...
        List<T> childJobsResults = new ArrayList<>();
        Throwable firstJobFailure = null;
        for (DqoQueueJob<T> childJob : this.childJobsSet) {
            DqoJobCompletionStatus childJobCompletionStatus = childJob.getCompletionStatus();
            if (childJobCompletionStatus == DqoJobCompletionStatus.SUCCEEDED) {
                T childJobResult = childJob.getResult();
                childJobsResults.add(childJobResult);
            } else if (childJobCompletionStatus == DqoJobCompletionStatus.FAILED) {
                if (firstJobFailure == null) {
                    firstJobFailure = childJob.getJobExecutionException();
                }
            }
        }

        if (firstJobFailure != null) {
            this.allJobsFinished.completeExceptionally(firstJobFailure);
        } else {
            this.allJobsFinished.complete(childJobsResults);
        }
        assert this.childJobsSet.isEmpty();
        return null;
    }

    /**
     * Pulls the next job from the queue.
     * @return The next child job (if available) or returns null when there are no more child jobs.
     */
    public DqoQueueJob<T> pullNextJob() {
        synchronized (this.lock) {
            if (this.remainingJobs.isEmpty()) {
                return null;
            }

            return this.remainingJobs.poll();
        }
    }

    /**
     * Waits for all the child jobs, retrieves the results of successfully finished jobs (not failed, not cancelled).
     * NOTE: the count of results will match the count of child jobs when all the jobs have finished successfully.
     * When any child job has cancelled or failed, the result list will not contain its result (it is skipped).
     * @param parentJobCancellationToken Job cancellation token of the parent's job. Because waiting for child jobs to finish is a potentially blocking (but a cancellable) operation,
     *                                   the method expects to receive the cancellation token of the parent job. A cancellation listener will be registered that will propagate
     *                                   the parent's job cancellation to child jobs which will be also cancelled or abandoned if they were not yet executed.
     * @return List of results returned by successfully finished child jobs.
     */
    public List<T> waitForChildResults(JobCancellationToken parentJobCancellationToken) {
        Consumer<JobCancellationToken> jobCancellationTokenConsumer = jobCancellationToken -> {
            this.cancel();
        };

        try (JobCancellationListenerHandle handle = parentJobCancellationToken.registerCancellationListener(jobCancellationTokenConsumer)) {
            try {
                return this.allJobsFinished.get();
            } catch (InterruptedException e) {
                throw new DqoQueueJobCancelledException();
            }
            catch (ExecutionException e) {
                if (e.getCause() instanceof DqoQueueJobCancelledException) {
                    throw new DqoQueueJobCancelledException();
                }

                throw new DqoQueueJobExecutionException(e.getCause());
            }
        }
    }

    /**
     * Cancels all child jobs. This operation should be called from a cancellation listener callback on the parent job,
     * when the parent job is cancelled, so the parent's job cancellation is propagated to child jobs.
     */
    public void cancel() {
        synchronized (this.lock) {
            this.remainingJobs.clear();
            if (!this.allJobsFinished.isDone()) {
                this.allJobsFinished.cancel(true);
            }
        }
    }
}
